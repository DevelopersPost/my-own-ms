package com.example.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunction {

    private static final Logger log = LoggerFactory.getLogger(MessageFunction.class);


    @Bean
    public Function<PersonMessageDto,PersonMessageDto> email() {
        return (personMessageDto) -> {
            log.info("Sending email with the details : " +  personMessageDto.toString());
            return personMessageDto;
        };
    }

    @Bean
    public Function<PersonMessageDto,PersonMessageDto> sms() {
        return (personMessageDto) -> {
            log.info("Sending sms with the details : " +  personMessageDto.toString());
            return personMessageDto;
        };
    }
}
