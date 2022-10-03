package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Banque;
import sn.youdev.model.Don;
import sn.youdev.model.Donneur;
import sn.youdev.model.serializable.EtatDon;

import java.util.List;
import java.util.Optional;
@Repository
public interface DonRepo extends JpaRepository<Don,String> {
    List<Don> findAllByDonneur(Donneur donneur);
    List<Don> findAllByOrderByDateAsc();
    List<Don> findAllByEtatOrderByDateAsc(EtatDon etat);
    List<Don> findByBanque(Banque banque);
    Optional<Don> findFirstByDonneurOrderByDateDesc(Donneur donneur);
}
