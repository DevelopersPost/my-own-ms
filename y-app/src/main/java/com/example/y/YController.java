package com.example.y;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.y.EmployeeRepository;

@RestController
public class YController {

    private static final Logger log = LoggerFactory.getLogger(YController.class);

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/y")
    public String y() {
        return "Y";
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
