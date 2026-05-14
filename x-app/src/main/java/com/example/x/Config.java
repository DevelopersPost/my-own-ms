package com.example.x;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class Config {

    @Bean
    public Consumer<PersonMessageDto> updateCommunication() {
        return personMessageDto -> {
            log.info("Updating Communication status for : " + personMessageDto.name());
        };
    }
}
