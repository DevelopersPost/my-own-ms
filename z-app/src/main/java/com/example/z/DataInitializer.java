package com.example.z;

import com.example.z.Employee;
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
            employee.setName("z");
            employee.setRole("Lead Developer z");
            employeeRepository.save(employee);
            System.out.println("Initialized employee data for z-app");
        }
    }
}
