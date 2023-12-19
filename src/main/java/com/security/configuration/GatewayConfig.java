package com.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RestController
public class GatewayConfig {

    @Autowired
    private HttpServletRequest request;

    @Bean
    public RouteLocator customRouteLocator() {

        return () -> Flux.just(
                Route.async()
                        .id("route1")
                        .uri("http://192.168.1.38:8081/")
                        .predicate(exchange -> exchange.getRequest().getPath().toString().startsWith("/timesheet/**"))
                        .build(),
                Route.async()
                        .id("route2")
                        .uri("http://localhost:1010/")
                        .predicate(exchange -> exchange.getRequest().getPath().toString().startsWith("/app/leave-request/**"))
                        .build(),
                Route.async()
                        .id("route3")
                        .uri("http://localhost:8082/")
                        .predicate(exchange -> exchange.getRequest().getPath().toString().startsWith("/employee/**"))
                        .build(),
                Route.async()
                        .id("route4")
                        .uri("http://localhost:1010/")
                        .predicate(exchange -> exchange.getRequest().getPath().toString().startsWith("/type/leave-type/**"))
                        .build(),
                Route.async()
                        .id("route5")
                        .uri("http://192.168.1.9:8081/")
                        .predicate(exchange -> exchange.getRequest().getPath().toString().startsWith("/project/**"))
                        .build());
    }
}