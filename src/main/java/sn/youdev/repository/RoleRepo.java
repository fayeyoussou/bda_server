package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByNomIgnoreCase(String nom);
}
