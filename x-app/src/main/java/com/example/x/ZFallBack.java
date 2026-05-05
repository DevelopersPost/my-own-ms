package com.example.x;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZFallBack implements ZAppFeignClient{

    @Override
    public List<Employee> getAllEmployees(String correlationId) {
        return List.of();
    }
}
