package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Localisation;

@Repository
public interface LocalisationRepo extends JpaRepository<Localisation,Long> {
}
