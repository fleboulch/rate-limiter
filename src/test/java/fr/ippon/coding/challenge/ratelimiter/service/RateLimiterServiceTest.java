package fr.ippon.coding.challenge.ratelimiter.service;

import fr.ippon.coding.challenge.ratelimiter.config.RateLimiterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class RateLimiterServiceTest {
    private static final ZonedDateTime FIXED_INSTANT = LocalDate.of(2020, 5, 24).atStartOfDay(ZoneId.systemDefault());
    private RateLimiterService service;
    private ClockTester clock;

    private static class ClockTester extends Clock {

        static ClockTester startAt(Instant instant, ZoneId zone) {
            return new ClockTester(instant, zone);
        }

        private Instant instant;
        private final ZoneId zone;

        private ClockTester(Instant instant, ZoneId zone) {
            this.instant = Objects.requireNonNull(instant, "Instant cannot be <null>");
            this.zone = Objects.requireNonNull(zone, "Zone cannot be <null>");
        }

        @Override
        public ZoneId getZone() {
            return this.zone;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            if (zone.equals(this.zone)) {
                return this;
            }
            return new ClockTester(instant, zone);
        }

        @Override
        public Instant instant() {
            return instant;
        }

        void advance(Duration duration) {
            instant = instant.plusNanos(duration.toNanos());
        }
    }

    @BeforeEach
    void setUp() {
        clock = ClockTester.startAt(FIXED_INSTANT.toInstant(), FIXED_INSTANT.getZone());
        service = new RateLimiterService(new RateLimiterProperties(2, Duration.ofSeconds(5)), clock);
    }

    @Test
    void checkRate() {
        service.checkRate();
        clock.advance(Duration.ofMillis(200));
        service.checkRate();
        clock.advance(Duration.ofMillis(200));

        assertThatThrownBy(
                () -> service.checkRate()
        ).isInstanceOf(MaxRequestNumberReachedException.class);

    }
}
