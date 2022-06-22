package io.ghostfolio.service;

import io.ghostfolio.client.rabbitmq.ActivityRow;
import io.ghostfolio.service.platformhandler.PlatformHandler;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RabbitListener
public class ActivityRowListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRowListener.class);

    private final SendActivitiesToGhostfolioService sendActivitiesToGhostfolioService;
    private final List<PlatformHandler> platformHandlers;

    public ActivityRowListener(SendActivitiesToGhostfolioService sendActivitiesToGhostfolioService, List<PlatformHandler> platformHandlers) {
        this.sendActivitiesToGhostfolioService = sendActivitiesToGhostfolioService;
        this.platformHandlers = platformHandlers;
    }

    @Queue("row")
    public void listen(ActivityRow activityRow) {
        LOGGER.info("received message {}", activityRow.row()[4]);

        platformHandlers
                .stream()
                .filter(handler -> handler.canHandle(activityRow.platform()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No platform handler found for platform %s", activityRow.platform())))
                .handle(activityRow.row())
                .ifPresent(activityBody -> sendActivitiesToGhostfolioService.sendActivities(List.of(activityBody), activityRow.token()));
    }

}
