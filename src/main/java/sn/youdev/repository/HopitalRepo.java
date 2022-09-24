package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Hopital;

@Repository
public interface HopitalRepo extends JpaRepository<Hopital,Long> {
}
