package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Article;
import sn.youdev.model.User;

import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article,Long> {
    Optional<Article> findByAutheur(User autheur);
    Optional<Article> findByTitreIgnoreCase(String titre);
}
