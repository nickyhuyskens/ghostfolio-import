package io.ghostfolio.client.rabbitmq;

import java.util.Map;

public record ActivityRow(String[] row, String platform, String token, Map<String, String> accountIdsByCurrency) {
}
