package io.ghostfolio.client.yahoo;

import java.util.List;

public record YahooFinanceSearchResponse(List<YahooFinanceQuoteResponse> quotes) {
}
