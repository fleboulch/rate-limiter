package fr.ippon.coding.challenge.ratelimiter.api;


import fr.ippon.coding.challenge.ratelimiter.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private RateLimiterService rateLimiter;

    public HelloController(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @GetMapping(path = "hello/{name}")
    public String sayHello(@PathVariable String name) {

        rateLimiter.checkRate();
        return "Hello " + name + " !";
    }
}
