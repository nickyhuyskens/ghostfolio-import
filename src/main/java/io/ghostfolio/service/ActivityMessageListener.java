package io.ghostfolio.service;

import io.ghostfolio.client.rabbitmq.ActivityMessage;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RabbitListener
public class ActivityMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityMessageListener.class);

    private final SendActivitiesToGhostfolioService sendActivitiesToGhostfolioService;

    public ActivityMessageListener(SendActivitiesToGhostfolioService sendActivitiesToGhostfolioService) {
        this.sendActivitiesToGhostfolioService = sendActivitiesToGhostfolioService;
    }

    @Queue("queue.rows")
    public void listen(ActivityMessage activityMessage) {
        LOGGER.info("received message {}", activityMessage);

        sendActivitiesToGhostfolioService.sendActivities(List.of(activityMessage.activityBody()), activityMessage.token());
    }

}
