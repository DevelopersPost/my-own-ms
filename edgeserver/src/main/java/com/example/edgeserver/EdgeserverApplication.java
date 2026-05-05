package com.example.edgeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class EdgeserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeserverApplication.class, args);
    }

    @Bean
    public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/app/x-app/**")
                        .filters( f -> f.rewritePath("/app/x-app/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("XAPPCB")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://X-APP"))
                .route(p -> p
                        .path("/app/y-app/**")
                        .filters( f -> f.rewritePath("/app/y-app/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://Y-APP"))
                .route(p -> p
                        .path("/app/z-app/**")
                        .filters( f -> f.rewritePath("/app/z-app/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://Z-APP"))
                .build();

    }

}
