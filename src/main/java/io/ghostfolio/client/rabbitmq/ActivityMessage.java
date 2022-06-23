package io.ghostfolio.client.rabbitmq;

import io.ghostfolio.client.ghostfolio.ActivityBody;

public record ActivityMessage (String token, ActivityBody activityBody) {
}
