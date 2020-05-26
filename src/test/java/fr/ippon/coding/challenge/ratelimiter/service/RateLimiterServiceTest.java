package fr.ippon.coding.challenge.ratelimiter.service;

import fr.ippon.coding.challenge.ratelimiter.config.RateLimiterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class RateLimiterServiceTest {

    public static final ZonedDateTime FIXED_INSTANT = LocalDate.of(2020, 5, 24).atStartOfDay(ZoneId.systemDefault());
    private RateLimiterService service;

//    @MockBean
    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(FIXED_INSTANT.toInstant(), ZoneId.systemDefault());
//        when(clock).thenReturn(Clock.fixed(FIXED_INSTANT.toInstant(), FIXED_INSTANT.getZone()));
        service = new RateLimiterService(new RateLimiterProperties(2, Duration.ofSeconds(5)), clock);
    }

    @Test
    void checkRate() {

        service.checkRate();
//        clock = Clock.tick(clock, Duration.ofDays(4));
//        when(clock.instant()).thenReturn(Clock.fixed(FIXED_INSTANT.toInstant(), FIXED_INSTANT.getZone()).instant());
//        when(Instant.now()).thenReturn(FIXED_INSTANT.plusSeconds(1).toInstant());
//        clock = Clock.offset(clock, Duration.ofMillis(200));
        service.checkRate();
//        clock = Clock.offset(clock, Duration.ofMillis(200));

        assertThatThrownBy(
                () -> service.checkRate()
        ).isInstanceOf(MaxRequestNumberReachedException.class);

    }
}
