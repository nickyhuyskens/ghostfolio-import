package io.ghostfolio.client.yahoo;

import java.util.List;

public record YahooFinanceChartResponse (List<YahooFinanceChartResultResponse> result) {
}
