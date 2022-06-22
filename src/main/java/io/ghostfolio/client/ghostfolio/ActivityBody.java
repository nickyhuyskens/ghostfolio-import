package io.ghostfolio.client.ghostfolio;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ActivityBody (@JsonProperty ActivityType type,
                            @JsonProperty String dataSource,
                            @JsonProperty String date,
                            @JsonProperty String currency,
                            @JsonProperty Double fee,
                            @JsonProperty Double quantity,
                            @JsonProperty String symbol,
                            @JsonProperty Double unitPrice,
                            @JsonProperty String accountId) {

}
