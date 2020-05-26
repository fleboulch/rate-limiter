package fr.ippon.coding.challenge.ratelimiter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterProperties {

    private int maxRequest;
    private Duration interval;

    public RateLimiterProperties() {
    }

    public RateLimiterProperties(int maxRequest, Duration interval) {
        this.maxRequest = maxRequest;
        this.interval = interval;
    }

    public int maxRequest() {
        return maxRequest;
    }

    public void setMaxRequest(int maxRequest) {
        this.maxRequest = maxRequest;
    }

    public Duration interval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }
}
