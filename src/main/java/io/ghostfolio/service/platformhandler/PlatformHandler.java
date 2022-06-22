package io.ghostfolio.service.platformhandler;

import io.ghostfolio.client.ghostfolio.ActivityBody;

import java.util.Optional;

public interface PlatformHandler {

    boolean canHandle(String platform);
    Optional<ActivityBody> handle(String[] row);

}
