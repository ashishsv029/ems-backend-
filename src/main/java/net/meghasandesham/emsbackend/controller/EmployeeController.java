package net.meghasandesham.emsbackend.controller;

import lombok.AllArgsConstructor;
import net.meghasandesham.emsbackend.dto.EmployeeDTO;
import net.meghasandesham.emsbackend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// We have layered architecture in Spring Boot
// Call Flow:--
// Client -> Controller -> Service -> Manager(optional) -> DAL (or Repository Layer or ORM Layer) -> DB
// Controller is like route handler, where we register route mapping and their corresponding handlers.. will call service and manage responses
// Service is like main place to implement data transformations and business logic
    // In some projects Service Layer takes care only of Data Transformations and Business Logic is maintained by Manager Layer
// DAL Layer (we can either write our custom code or use JPA repositories or use ORMs to talk to DB using drivers)
// Data Flow:-
// objectDTO -> objectEntity (vv while responding) Handled in service layer

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    //Dependency Injection of EmployeeService dependency - initialization is taken care by spring IOC Container (as we declared the Employee Service as Component (i. Service))
    //The allArgsConstructor will take the instance of Employee Service as an argument (provided by IOC) and assigns to below variable
    //If not using allArgsConstructor, then we need to define our own constructor and inject using @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')") // METHOD LEVEL SECURITY -> NEEDS TO ADD @EnableMethodSecurity annotation in security config class
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO EmployeeDTO) {
        EmployeeDTO savedEmployee = employeeService.createEmployee(EmployeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Build Get By Employee ID REST API
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") UUID employeeId){
        EmployeeDTO EmployeeDTO = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(EmployeeDTO);
    }

    // Build Get All Employees REST API
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Build Update Employee REST API
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") UUID employeeId,
                                                      @RequestBody EmployeeDTO updatedEmployee){
        EmployeeDTO EmployeeDTO = employeeService.updateEmployee(employeeId, updatedEmployee);
        return ResponseEntity.ok(EmployeeDTO);
    }

    // Build Delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully!.");
    }

}
