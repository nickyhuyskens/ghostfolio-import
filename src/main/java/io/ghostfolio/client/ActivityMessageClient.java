package io.ghostfolio.client;

import io.ghostfolio.client.rabbitmq.ActivityMessage;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("topic.rows")
public interface ActivityMessageClient {

    void sendActivity(ActivityMessage activityMessage);

}
