package io.ghostfolio.config;

import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RabbitMQChannelConfig extends ChannelInitializer {

    @Override
    public void initialize(Channel channel, String name) throws IOException {
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 100);
        channel.exchangeDeclare("topic.rows", "topic", false);
        channel.queueDeclare("queue.rows", false, false, false, args);

        channel.queueBind("queue.rows", "topic.rows", "#");
    }

}

