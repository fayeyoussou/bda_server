package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.youdev.model.JourneeDon;
import sn.youdev.model.serializable.JourneeDonId;

import java.util.List;

public interface JourneeDonRepo extends JpaRepository<JourneeDon, JourneeDonId> {
    List<JourneeDon> findByJournee_id(Long id);
}
