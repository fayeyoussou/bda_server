package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Hopital;
import sn.youdev.model.MedecinHopital;
import sn.youdev.model.User;
import sn.youdev.model.serializable.MedecinHopitalId;

import java.util.List;
import java.util.Optional;
@Repository
public interface MedecinHopitalRepo extends JpaRepository<MedecinHopital, MedecinHopitalId> {
    Optional<MedecinHopital> findByUser(User user);
    List<MedecinHopital> findByHopital(Hopital hopital);
}
