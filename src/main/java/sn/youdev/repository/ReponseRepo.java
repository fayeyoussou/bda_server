package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Demande;
import sn.youdev.model.Reponse;
import sn.youdev.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository

public interface ReponseRepo extends JpaRepository<Reponse,Long> {
    Optional<Reponse> findByDemande(Demande demande);
    List<Reponse> findAllByRepondeur(User repondeur);
    Optional<Reponse> findByDate(Date date);
}
