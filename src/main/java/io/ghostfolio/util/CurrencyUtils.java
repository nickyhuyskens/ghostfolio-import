package io.ghostfolio.util;

import java.util.Map;

public class CurrencyUtils {

    private final static Map<String, String> currencies = Map.of(
            "GBX", "GBp"
    );

    public static String getCurrency(String symbol) {
        return currencies.getOrDefault(symbol, symbol);
    }
}
