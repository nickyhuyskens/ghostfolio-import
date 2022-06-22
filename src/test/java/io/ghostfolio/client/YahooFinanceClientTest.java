package io.ghostfolio.client;

import io.ghostfolio.client.yahoo.YahooFinanceInfoForSymbolResponse;
import io.ghostfolio.client.yahoo.YahooFinanceSearchResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class YahooFinanceClientTest {

    @Inject
    private YahooFinanceClient yahooFinanceClient;

    @Test
    public void test() {
        HttpResponse<YahooFinanceSearchResponse> response = yahooFinanceClient.findSymbol("AAPL");
        assertThat(response.body().quotes()).hasSize(7);
    }

    @Test
    public void test2() {
        HttpResponse<YahooFinanceInfoForSymbolResponse> response = yahooFinanceClient.getInfoForSymbol("AAPL");

        assertThat(response.body().chart().result().get(0).meta().currency()).isEqualTo("USD");
    }
}
