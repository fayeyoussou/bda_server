package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Donneur;
import sn.youdev.model.Resultat;

import java.util.List;
@Repository
public interface ResultatRepo extends JpaRepository<Resultat,String> {
    List<Resultat> findAllByDon_Donneur(Donneur donneur);
}
