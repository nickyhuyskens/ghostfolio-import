package io.ghostfolio.util;

import java.util.AbstractMap;
import java.util.Map;

@Deprecated
public class SymbolUtils {

    private final static Map<String, String> symbols = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("BMW3", "BMW3.DE"),
            new AbstractMap.SimpleEntry<>("VFEM", "VFEM.L"),
            new AbstractMap.SimpleEntry<>("TTE", "TTE.PA"),
            new AbstractMap.SimpleEntry<>("3NFL", "3NFL.L"),
            new AbstractMap.SimpleEntry<>("RDSB", "SHEL.L"),
            new AbstractMap.SimpleEntry<>("BP", "BP.L"),
            new AbstractMap.SimpleEntry<>("WKL", "WKL.AS"),
            new AbstractMap.SimpleEntry<>("NOVN", "NOVN.SW"),
            new AbstractMap.SimpleEntry<>("NESN", "NESN.SW"),
            new AbstractMap.SimpleEntry<>("FME", "FME.DE"),
            new AbstractMap.SimpleEntry<>("DSM", "DSM.AS"),
            new AbstractMap.SimpleEntry<>("BATS", "BATS.L"),
            new AbstractMap.SimpleEntry<>("RED", "RED.MC"),
            new AbstractMap.SimpleEntry<>("RMS", "RMS.PA")
    );

    public static String getSymbol(String symbol) {
        return symbols.getOrDefault(symbol, symbol);
    }
}
