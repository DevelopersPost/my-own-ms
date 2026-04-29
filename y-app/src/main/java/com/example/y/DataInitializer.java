package com.example.y;

import com.example.y.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public DataInitializer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            Employee employee = new Employee();
            employee.setName("y");
            employee.setRole("Lead Developer y");
            employeeRepository.save(employee);
            System.out.println("Initialized employee data for y-app");
        }
    }
}
