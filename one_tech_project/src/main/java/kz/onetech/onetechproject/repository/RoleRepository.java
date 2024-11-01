package kz.onetech.onetechproject.repository;

import kz.onetech.onetechproject.model.ERole;
import kz.onetech.onetechproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
