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

    private final Duration intervalDuration;
    private final int nbMaxRequestsPerInterval;

    private final Set<Instant> dateEndpointCalls = new HashSet<>();

    private final Clock clock;

    public RateLimiterService(RateLimiterProperties rateLimiterProperties, Clock clock) {
        intervalDuration = rateLimiterProperties.interval();
        nbMaxRequestsPerInterval = rateLimiterProperties.maxRequest();
        this.clock = clock;
    }

    public void checkRate() {
        Instant now = Instant.now(clock);
        dateEndpointCalls.add(now);

        long numberOfRequestsInInterval = dateEndpointCalls.stream()
                .filter(endpointDateCalls -> isCallInInterval(endpointDateCalls, now))
                .count();

        if (numberOfRequestsInInterval > nbMaxRequestsPerInterval) {
            throw new MaxRequestNumberReachedException("Number of requests exceeded!");
        }

    }

    private boolean isCallInInterval(Instant endpointDateCall, Instant now) {

        return endpointDateCall.isAfter(now.minus(intervalDuration)) &&
                endpointDateCall.isBefore(now.plusNanos(1));
    }
}
