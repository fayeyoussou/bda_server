package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import sn.youdev.model.Article;
import sn.youdev.model.Journee;
import sn.youdev.model.User;

import java.util.Optional;

@Repository
public interface JourneeRepo extends JpaRepository<Journee,Long> {
    Optional<Journee> findByOrganisateur(User user);
    Optional<Journee> findByAutorisateur(User autorisateur);
//    Optional<Journee>findByArticle(Article article);
}
