package io.ghostfolio.client;

import io.ghostfolio.client.rabbitmq.ActivityRow;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient
public interface ActivityRowClient {

    @Binding("row")
    void sendActivity(ActivityRow activityRow);

}
