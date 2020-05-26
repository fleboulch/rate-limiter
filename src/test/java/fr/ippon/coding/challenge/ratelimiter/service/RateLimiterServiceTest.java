package fr.ippon.coding.challenge.ratelimiter.service;

import fr.ippon.coding.challenge.ratelimiter.config.RateLimiterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateLimiterServiceTest {

    private static final Instant NOW = LocalDate.of(2020, 5, 24).atStartOfDay(ZoneId.systemDefault()).toInstant();
    private static final Duration TEN_SECONDS = Duration.ofSeconds(10);
    private static final Duration TEN_MILLIS = Duration.ofMillis(10);
    private static final int NB_MAX_REQUESTS_PER_INTERVAL = 2;
    private static final Duration INTERVAL_DURATION = Duration.ofSeconds(5);

    private RateLimiterService service;

    @Mock
    private Clock clock;
    private Instant currentTime;

    @BeforeEach
    void setUp() {
        initTime(NOW);
        service = new RateLimiterService(new RateLimiterProperties(NB_MAX_REQUESTS_PER_INTERVAL, INTERVAL_DURATION), clock);
    }

    private void initTime(Instant now) {
        currentTime = now;
        when(clock.instant()).thenReturn(currentTime);
    }

    @Test
    void calling_every_ten_millis_is_forbidden_because_the_limit_is_two_calls_every_five_seconds() {

        service.checkRate();
        goInTheFuture(TEN_MILLIS);

        service.checkRate();
        goInTheFuture(TEN_MILLIS);

        assertThatThrownBy(
                () -> service.checkRate()
        ).isInstanceOf(MaxRequestNumberReachedException.class);

    }

    @Test
    void calling_every_ten_seconds_is_allowed_because_the_limit_is_two_calls_every_five_seconds() {

        for (int i = 0; i < 5; i++) {
            service.checkRate();
            goInTheFuture(TEN_SECONDS);

        }
        service.checkRate();

    }

    private void goInTheFuture(Duration duration) {
        initTime(currentTime.plus(duration));
    }
}
