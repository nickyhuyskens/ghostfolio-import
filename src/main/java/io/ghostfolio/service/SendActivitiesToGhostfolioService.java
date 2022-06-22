package io.ghostfolio.service;

import io.ghostfolio.client.GhostfolioClient;
import io.ghostfolio.client.ghostfolio.ActivityBodies;
import io.ghostfolio.client.ghostfolio.ActivityBody;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Named
public class SendActivitiesToGhostfolioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendActivitiesToGhostfolioService.class);

    private final GhostfolioClient ghostfolioClient;

    public SendActivitiesToGhostfolioService(GhostfolioClient ghostfolioClient) {
        this.ghostfolioClient = ghostfolioClient;
    }

    public void sendActivities(List<ActivityBody> activities, String token) {
        final AtomicInteger counter = new AtomicInteger();

        List<ActivityBodies> activityBodies = activities.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 20))
                .values()
                .stream()
                .map(ActivityBodies::new)
                .collect(Collectors.toList());

        activityBodies.forEach(activitiesBody -> {
            int activitiesToSend = activitiesBody.activities().size();
            LOGGER.info("Sending {} activities", activitiesToSend);
            try {
                ghostfolioClient.importActivities(token, activitiesBody);
            } catch (Exception e) {
                if (e.getMessage().contains("duplicate")) {
                    LOGGER.info("Ignore duplicate");
                } else {
                    throw e;
                }
            }

            LOGGER.info("Sent {} activities", activitiesToSend);
        });
    }
}
