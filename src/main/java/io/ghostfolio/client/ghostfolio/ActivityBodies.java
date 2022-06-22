package io.ghostfolio.client.ghostfolio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ActivityBodies(@JsonProperty List<ActivityBody> activities) {
}
