package io.ghostfolio.service.platformhandler;

import io.ghostfolio.client.ghostfolio.ActivityBody;
import io.ghostfolio.client.ghostfolio.ActivityType;
import io.ghostfolio.service.YahooTickerService;
import io.ghostfolio.util.CurrencyUtils;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Named
public class Trading212PlatformHandler implements PlatformHandler {

    private final YahooTickerService yahooTickerService;

    public Trading212PlatformHandler(YahooTickerService yahooTickerService) {
        this.yahooTickerService = yahooTickerService;
    }


    @Override
    public boolean canHandle(String platform) {
        return platform.equals("Trading212");
    }

    @Override
    public Optional<ActivityBody> handle(String[] row) {
        return getActivityFromRow(row);
    }

    private Optional<ActivityBody> getActivityFromRow(String[] row) {
        if (row[0].equals("Deposit") || row[0].equals("Withdrawal")) {
            return Optional.empty();
        }

        return Optional.ofNullable(yahooTickerService.getCorrectTicker(row[3], CurrencyUtils.getCurrency(row[7]), row[4]))
                .map(symbol -> new ActivityBody(
                        getAction(row[0]),
                        "YAHOO",
                        LocalDateTime.parse(row[1], DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")).toString(),
                        CurrencyUtils.getCurrency(row[7]),
                        0.0,
                        Double.parseDouble(row[5]),
                        symbol,
                        Double.parseDouble(row[6]),
                        getAccountIdForCurrency(row[7])));
    }

    private String getAccountIdForCurrency(String s) {
        return switch (s) {
            case "EUR" -> "e07a5706-be52-40b4-8668-de22a5673448";
            case "USD" -> "fcd5cf3e-4f35-4760-bc90-652b4841dfb2";
            case "GBP" -> "b99f5c84-aeed-40f2-94cb-55bae048f577";
            case "GBX" -> "ee17dc3a-b667-4b03-aa64-e69f1f8a689e";
            case "CHF" -> "68c82b9e-b030-435a-bb4c-3f3d1b129637";
            default -> throw new IllegalArgumentException(String.format("No accountId found for currency %s", s));
        };
    }

    private ActivityType getAction(String activity) {
        return switch (activity) {
            case "Market buy" -> ActivityType.BUY;
            case "Market sell" -> ActivityType.SELL;
            case "Dividend (Ordinary)", "Dividend (Demerger)", "Dividend (Return of capital)" -> ActivityType.DIVIDEND;
            default -> throw new IllegalArgumentException(String.format("No activity type for activity %s", activity));
        };
    }
}