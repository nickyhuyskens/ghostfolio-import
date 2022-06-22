package io.ghostfolio.client;

import io.ghostfolio.client.ghostfolio.ActivityBodies;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value = "https://ghostfol.io/api/v1")
public interface GhostfolioClient {

    @Post(value = "/import", consumes = MediaType.TEXT_PLAIN, produces = MediaType.APPLICATION_JSON, single = true)
    HttpResponse importActivities(@Header(name = "Authorization") String authorization, @Body ActivityBodies activityBodies);

}
