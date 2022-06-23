package io.ghostfolio.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class YahooTickerServiceTest {

    @Inject
    private YahooTickerService yahooTickerService;

    @Test
    public void test() {
        assertThat(yahooTickerService.getCorrectTicker("AAPL", "USD", "Apple")).isEqualTo("AAPL");
        assertThat(yahooTickerService.getCorrectTicker("AAPL", "CAD", "Apple")).isEqualTo("AAPL.NE");
        assertThat(yahooTickerService.getCorrectTicker("RMS", "EUR", "Something")).isEqualTo("RMS.PA");
        assertThat(yahooTickerService.getCorrectTicker("NESN", "CHF", "Something")).isEqualTo("NESN.SW");
        assertThat(yahooTickerService.getCorrectTicker("RED", "EUR", "Red Electrica Corporacion")).isEqualTo("RED.MC");
        assertThat(yahooTickerService.getCorrectTicker("RDSB", "GBp", "Shell")).isEqualTo("SHEL.L");
        assertThat(yahooTickerService.getCorrectTicker("OR", "EUR", "L'Oreal")).isEqualTo("OR.PA");
        assertThat(yahooTickerService.getCorrectTicker("CWEN/A", "USD", "Clearway Energy - Class A")).isEqualTo("CWEN-A");
    }

}
