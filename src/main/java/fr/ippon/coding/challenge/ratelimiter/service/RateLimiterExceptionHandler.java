package fr.ippon.coding.challenge.ratelimiter.service;

import fr.ippon.coding.challenge.ratelimiter.api.HelloController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = HelloController.class)
public class RateLimiterExceptionHandler {

    @ExceptionHandler(value = MaxRequestNumberReachedException.class)
    private ResponseEntity<?> handleTooManyRequest() {
        return new ResponseEntity(HttpStatus.TOO_MANY_REQUESTS);
    }
}
