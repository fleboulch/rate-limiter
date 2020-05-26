package fr.ippon.coding.challenge.ratelimiter.service;

public class MaxRequestNumberReachedException extends RuntimeException {
    public MaxRequestNumberReachedException(String message) {
        super(message);
    }
}
