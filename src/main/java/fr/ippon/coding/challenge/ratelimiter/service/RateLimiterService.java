package fr.ippon.coding.challenge.ratelimiter.service;

import fr.ippon.coding.challenge.ratelimiter.config.RateLimiterProperties;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class RateLimiterService {

    private static Duration INTERVAL_DURATION;
    private static int NB_MAX_REQUESTS_PER_INTERVAL;

    private static Set<Instant> DATE_ENDPOINT_CALLS = new HashSet<>();

    private Clock clock;

    public RateLimiterService(RateLimiterProperties rateLimiterProperties, Clock clock) {
        INTERVAL_DURATION = rateLimiterProperties.interval();
        NB_MAX_REQUESTS_PER_INTERVAL = rateLimiterProperties.maxRequest();
        this.clock = clock;
    }

    public void checkRate() {
        Instant now = Instant.now(clock);
        DATE_ENDPOINT_CALLS.add(now);

        long numberOfRequestsInInterval = DATE_ENDPOINT_CALLS.stream()
                .filter(endpointDateCalls -> isCallInInterval(endpointDateCalls, now))
                .count();

        if (numberOfRequestsInInterval > NB_MAX_REQUESTS_PER_INTERVAL) {
            throw new MaxRequestNumberReachedException("Number of requests exceeded!");
        }

    }

    private boolean isCallInInterval(Instant endpointDateCall, Instant now) {

        return endpointDateCall.isAfter(now.minus(INTERVAL_DURATION)) &&
                endpointDateCall.isBefore(now.plusNanos(1));
    }
}
