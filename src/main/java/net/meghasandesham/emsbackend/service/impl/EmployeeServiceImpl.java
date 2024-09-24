package net.meghasandesham.emsbackend.service.impl;

import lombok.AllArgsConstructor;
import net.meghasandesham.emsbackend.dto.EmployeeDTO;
import net.meghasandesham.emsbackend.entity.Employee;
import net.meghasandesham.emsbackend.exception.ResourceNotFoundException;
import net.meghasandesham.emsbackend.mapper.EmployeeMapper;
import net.meghasandesham.emsbackend.repository.EmployeeRepository;
import net.meghasandesham.emsbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service // This will push spring to create a spring bean for this class (similar to @Component)
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // convert dto to entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);
        // work on the employee entity
        employeeRepository.save(employee);
        // convert entity to DTO
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> //Lambda Function
                        new ResourceNotFoundException("No Employee exists with given id : " + employeeId));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(UUID employeeId, EmployeeDTO updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee not exists with given id: " + employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmailId(updatedEmployee.getEmailId());
        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(UUID employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
        );

        employeeRepository.deleteById(employeeId);
    }
}
