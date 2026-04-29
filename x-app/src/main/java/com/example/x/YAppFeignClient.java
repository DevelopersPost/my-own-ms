package com.example.x;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "y-app")
public interface YAppFeignClient {

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestHeader("app-correlation-id") String correlationId);
}
