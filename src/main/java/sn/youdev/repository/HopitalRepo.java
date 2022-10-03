package sn.youdev.repository;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Hopital;

import java.util.List;

@Repository
public interface HopitalRepo extends JpaRepository<Hopital,Long> {
    List<Hopital> findAllByEtatTrue();
}
