package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.InfoPerso;

import java.util.Optional;

@Repository
public interface InfoRepo extends JpaRepository<InfoPerso,Long> {
    Optional<InfoPerso> findByEmailIgnoreCase(String email);
    Optional<InfoPerso> findByCin(String cin);
    Optional<InfoPerso> findByTelephone(String telephone);
}
