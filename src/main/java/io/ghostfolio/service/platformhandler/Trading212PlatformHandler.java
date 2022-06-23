package io.ghostfolio.service.platformhandler;

import io.ghostfolio.client.ghostfolio.ActivityBody;
import io.ghostfolio.client.ghostfolio.ActivityType;
import io.ghostfolio.client.rabbitmq.ActivityRow;
import io.ghostfolio.service.YahooTickerService;
import io.ghostfolio.util.CurrencyUtils;
import io.micronaut.core.util.StringUtils;
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
    public Optional<ActivityBody> handle(ActivityRow row) {
        return getActivityFromRow(row);
    }

    private Optional<ActivityBody> getActivityFromRow(ActivityRow activityRow) {
        var row = activityRow.row();
        if (row[0].equals("Deposit") || row[0].equals("Withdrawal")) {
            return Optional.empty();
        }

        String accountId = activityRow.accountIdsByCurrency().get(row[7]);

        if (StringUtils.isEmpty(accountId)) {
            throw new IllegalArgumentException(String.format("No accountId found for currency %s", row[7]));
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
                        accountId));
    }

    private ActivityType getAction(String activity) {
        return switch (activity) {
            case "Market buy" -> ActivityType.BUY;
            case "Market sell" -> ActivityType.SELL;
            case "Dividend (Ordinary)", "Dividend (Demerger)", "Dividend (Return of capital)", "Dividend (Bonus)", "Dividend (Ordinary manufactured payment)" -> ActivityType.DIVIDEND;
            default -> throw new IllegalArgumentException(String.format("No activity type for activity %s", activity));
        };
    }
}
