package com.example.edgeserver;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class CustomFilter {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomFilter.class);

    public static final String CORRELATION_ID = "app-correlation-id";


    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Bean
    @Order(1)
    public GlobalFilter requestTraceFilter() {
        return (exchange, chain) -> {
            HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
            String correlationId = requestHeaders.getFirst("app-correlation-id");

            if (correlationId != null) {
                log.debug("app-correlation-id found in RequestTraceFilter : {}", correlationId);
            } else {
                String newCorrelationId = java.util.UUID.randomUUID().toString();
                exchange = exchange.mutate()
                        .request(exchange.getRequest().mutate()
                                .header("app-correlation-id", newCorrelationId)
                                .build())
                        .build();
                log.debug("app-correlation-id generated in RequestTraceFilter : {}", newCorrelationId);
            }

            return chain.filter(exchange);
        };
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = getCorrelationId(requestHeaders);
                log.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
            }));
        };
    }

}
