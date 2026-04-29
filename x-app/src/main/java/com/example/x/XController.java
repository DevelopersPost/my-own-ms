package com.example.x;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.x.EmployeeRepository;

@RestController
public class XController {

    private static final Logger log = LoggerFactory.getLogger(XController.class);

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ZAppFeignClient zAppFeignClient;

    @Autowired
    private YAppFeignClient yAppFeignClient;

    @GetMapping("/x")
    public String x() {
        return "X";
    }

    @GetMapping("/version")
    public String version() {
        return buildVersion;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/all-employees")
    public Map<String, List<Employee>> getAllEmployeesFromApps(@RequestHeader("app-correlation-id") String correlationId) {
        Map<String, List<Employee>> response = new HashMap<>();
        log.debug("correlation-id={}", correlationId);
        try {
            List<Employee> zAppEmployees = zAppFeignClient.getAllEmployees(correlationId);
            response.put("z-app", zAppEmployees);
        } catch (Exception e) {
            response.put("z-app", new ArrayList<>());
        }

        try {
            List<Employee> yAppEmployees = yAppFeignClient.getAllEmployees(correlationId);
            response.put("y-app", yAppEmployees);
        } catch (Exception e) {
            response.put("y-app", new ArrayList<>());
        }


        List<Employee> allEmployees = getAllEmployees();
        response.put("x-app", allEmployees);

        return response;
    }


}
