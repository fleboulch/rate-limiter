# Rate Limiters
## Make it work
Complete the code so that the application can properly say hello, given the following request : 

`http://localhost:8080/hello/{name}`

## Throttle the requests
Implement a mechanism that allows the application to only answer to 2 requests by each 5 seconds.
In case the maximum request have be reached in the given amount of time, reply with an HTTP 429 code.

## Load the config from the config file
Use the configuration from the `application.yml` file

## Multi clustering ?
How would you develop your rate limiter for it to work on multiple instances ?

## Hot config reload
Implement a mechanism that allows the limiter configuration to be reloaded without service
outage (change the window period for instance)