package com.example.z;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.z.EmployeeRepository;

@RestController
public class ZController {

    private static final Logger log = LoggerFactory.getLogger(ZController.class);

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/z")
    public String z() {
        return "Z";
    }

    @GetMapping("/version")
    public String version() {
        return buildVersion;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(@RequestHeader("app-correlation-id") String correlationId) {
        log.debug("correlation-id={}", correlationId);
        return employeeRepository.findAll();
    }
}
