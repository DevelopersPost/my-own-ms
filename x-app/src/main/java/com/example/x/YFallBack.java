package com.example.x;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YFallBack implements YAppFeignClient{

    @Override
    public List<Employee> getAllEmployees(String correlationId) {
        return List.of();
    }
}
