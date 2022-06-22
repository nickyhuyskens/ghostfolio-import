package io.ghostfolio.service;

import io.ghostfolio.client.ActivityRowClient;
import io.ghostfolio.client.rabbitmq.ActivityRow;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Named
public class ProcessSheetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessSheetService.class);

    private final CsvReader csvReader;
    private final ActivityRowClient rabbitMqClient;

    public ProcessSheetService(CsvReader csvReader, ActivityRowClient rabbitMqClient) {
        this.csvReader = csvReader;
        this.rabbitMqClient = rabbitMqClient;
    }

    public void processSheet(CompletedFileUpload csv, String token) throws IOException {
        List<String[]> rows = csvReader.processSheet(csv);

        rows.forEach(row ->
                rabbitMqClient.sendActivity(new ActivityRow(row, "Trading212", token)));
    }

}
