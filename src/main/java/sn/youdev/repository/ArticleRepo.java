package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Article;
import sn.youdev.model.User;
import sn.youdev.model.serializable.TypeArticle;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article,Long> {
    List<Article> findAllByAuteurAndValideTrueAndDisponibleTrueOrderByDatePublicationDesc(User auteur);
    Optional<Article> findByTitreAndValideTrueAndDisponibleTrueIgnoreCaseOrderByDatePublicationDesc(String titre);
    List<Article> findAllByValideTrueAndDisponibleTrueOrderByDatePublicationDesc();
    List<Article> findAllByValideFalseAndDisponibleTrueOrderByDatePublicationDesc();
    List<Article> findAllByTypeAndValideTrueAndDisponibleTrueOrderByDatePublicationDesc(TypeArticle typeArticle);
    Optional<Article>findByIdAndValideTrueAndDisponibleTrue(Long id);
}
