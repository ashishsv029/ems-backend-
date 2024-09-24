package net.meghasandesham.emsbackend.mapper;

import net.meghasandesham.emsbackend.dto.EmployeeDTO;
import net.meghasandesham.emsbackend.entity.Employee;

// This class holds utility functions, So make it as static
// This mapper class is used to transform DTO object (which comes as request payload in controller class from the client) into Employee Object
// The service/manager classes should always deal with Employee (entity) classes only
// While responding we need to convert the modified Employee entity object back to DTO object and return
public class EmployeeMapper {
    public static EmployeeDTO mapToEmployeeDto(Employee employee){
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmailId()
        );
    }

    public static Employee mapToEmployee(EmployeeDTO employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmailId()
        );
    }
}
