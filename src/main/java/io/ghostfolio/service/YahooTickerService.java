package io.ghostfolio.service;

import io.ghostfolio.client.YahooFinanceClient;
import io.ghostfolio.client.yahoo.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Named;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Named
public class YahooTickerService {

    private final YahooFinanceClient yahooFinanceClient;
    private final StatefulRedisConnection<String, String> redisConnection;

    public YahooTickerService(YahooFinanceClient yahooFinanceClient, StatefulRedisConnection<String, String> redisConnection) {
        this.yahooFinanceClient = yahooFinanceClient;
        this.redisConnection = redisConnection;
    }

    public String getCorrectTicker(String symbol, String currency, String name) {
        RedisCommands<String, String> commands = redisConnection.sync();

        String resultFromRedis = commands.get(symbol + currency);
        if (StringUtils.isNotEmpty(resultFromRedis)) {
            return resultFromRedis;
        }

        List<YahooFinanceQuoteResponse> possibleSymbols = yahooFinanceClient.findSymbol(symbol)
                .body()
                .quotes()
                .stream()
                .filter(quote -> quote.symbol().equals(symbol) || (symbol.endsWith(".") ? quote.symbol().startsWith(symbol) : quote.symbol().startsWith(symbol + ".")) || (quote.longname() != null && quote.longname().contains(name)))
                .collect(toList());

        //if (possibleSymbols.isEmpty()) {
        possibleSymbols.addAll(yahooFinanceClient.findSymbol(name)
                .body()
                .quotes()
                .stream()
                .filter(quote -> quote.symbol().equals(symbol) || (symbol.endsWith(".") ? quote.symbol().startsWith(symbol) : quote.symbol().startsWith(symbol + ".")) || (quote.longname() != null && quote.longname().contains(name)))
                .collect(toList()));
        //}
        return possibleSymbols.stream()
                .distinct()
                .map(quote -> yahooFinanceClient.getInfoForSymbol(quote.symbol()))
                .map(HttpResponse::body)
                .map(YahooFinanceInfoForSymbolResponse::chart)
                .flatMap((YahooFinanceChartResponse yahooFinanceChartResponse) -> yahooFinanceChartResponse.result().stream())
                .map(YahooFinanceChartResultResponse::meta)
                .filter(meta -> meta.currency().equals(currency))
                .findFirst()
                .map(yahooFinanceMetaResponse -> {
                    commands.set(symbol + currency, yahooFinanceMetaResponse.symbol());
                    return yahooFinanceMetaResponse.symbol();
                }).orElseThrow(() -> new IllegalStateException(String.format("No Ticker found for Symbol %s and currency %s", symbol, currency)));
    }
}
