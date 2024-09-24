package net.meghasandesham.emsbackend.service;

import net.meghasandesham.emsbackend.dto.EmployeeDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO EmployeeDTO);
    EmployeeDTO getEmployeeById(UUID employeeId);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(UUID employeeId, EmployeeDTO updatedEmployee);
    void deleteEmployee(UUID employeeId);
}
