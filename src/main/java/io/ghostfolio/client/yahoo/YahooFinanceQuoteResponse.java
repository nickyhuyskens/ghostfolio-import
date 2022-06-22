package io.ghostfolio.client.yahoo;

public record YahooFinanceQuoteResponse(String exchange, String shortname, String longname, String symbol) {
}
