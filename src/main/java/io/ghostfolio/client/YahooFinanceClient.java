package io.ghostfolio.client;

import io.ghostfolio.client.yahoo.YahooFinanceInfoForSymbolResponse;
import io.ghostfolio.client.yahoo.YahooFinanceSearchResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client(value = "https://query1.finance.yahoo.com")
public interface YahooFinanceClient {

    @Get(value = "/v1/finance/search", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<YahooFinanceSearchResponse> findSymbol(@QueryValue String q);

    @Get(value = "/v8/finance/chart/{symbol}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<YahooFinanceInfoForSymbolResponse> getInfoForSymbol(@PathVariable("symbol") String symbol);

}
