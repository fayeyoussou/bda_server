package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Banque;

import java.util.Optional;

@Repository
public interface BanqueRepo extends JpaRepository<Banque,Long> {
    Optional<Banque> findByNomIgnoreCase(String nom);
}
