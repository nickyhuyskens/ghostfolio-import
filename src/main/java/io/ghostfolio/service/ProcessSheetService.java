package io.ghostfolio.service;

import io.ghostfolio.client.ActivityMessageClient;
import io.ghostfolio.client.rabbitmq.ActivityMessage;
import io.ghostfolio.client.rabbitmq.ActivityRow;
import io.ghostfolio.service.platformhandler.PlatformHandler;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Named
public class ProcessSheetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessSheetService.class);

    private final CsvReader csvReader;
    private final ActivityMessageClient rabbitMqClient;
    private final List<PlatformHandler> platformHandlers;

    public ProcessSheetService(CsvReader csvReader, ActivityMessageClient rabbitMqClient, List<PlatformHandler> platformHandlers) {
        this.csvReader = csvReader;
        this.rabbitMqClient = rabbitMqClient;
        this.platformHandlers = platformHandlers;
    }

    public void processSheet(CompletedFileUpload csv, String platform, String token, Map<String, String> accountIdsByCurrency) throws IOException {
        List<String[]> rows = csvReader.processSheet(csv);

        rows.forEach(row -> {
            ActivityRow activityRow = new ActivityRow(row, platform, token, accountIdsByCurrency);
            platformHandlers
                    .stream()
                    .filter(handler -> handler.canHandle(activityRow.platform()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("No platform handler found for platform %s", activityRow.platform())))
                    .handle(activityRow)
                    .map(activityBody -> new ActivityMessage(token, activityBody))
                    .ifPresent(rabbitMqClient::sendActivity);
        });
    }
}
