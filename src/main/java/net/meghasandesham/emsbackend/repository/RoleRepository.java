package net.meghasandesham.emsbackend.repository;

import net.meghasandesham.emsbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
