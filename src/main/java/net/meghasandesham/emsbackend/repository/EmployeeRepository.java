package net.meghasandesham.emsbackend.repository;

import net.meghasandesham.emsbackend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// The JPA Repository provides all basic CRUD operations to interact with db like (filterById(), deleteById() etc)
// We can also define any other custom functions as well

// The parent interface is annotated with @Repository (which acts like a @Component and the instance is managed by Spring IOC as a bean)
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
