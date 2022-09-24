package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Banque;
import sn.youdev.model.Don;
import sn.youdev.model.Donneur;

import java.util.List;
import java.util.Optional;
@Repository
public interface DonRepo extends JpaRepository<Don,String> {
    List<Don> findByDonneur(Donneur donneur);
    List<Don> findByBanque(Banque banque);
    Optional<Don> findFirstByDonneurOrderByDateDesc(Donneur donneur);
}
