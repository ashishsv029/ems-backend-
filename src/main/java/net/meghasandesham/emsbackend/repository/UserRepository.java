package net.meghasandesham.emsbackend.repository;

import net.meghasandesham.emsbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> { //Internally Spring uses PROXY PATTERN to implement JPA Repository and thus provides CRUD functions
    // we get basic implementation of all CRUD based queries. We can also add any custom function definition and implement manually for any other custom complex query
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    // SPRING AUTOMATICALLY CREATES THOSE FUNCTIONS IMPLEMENTATIONS BASED ON NAME OF FUNCTION
}
