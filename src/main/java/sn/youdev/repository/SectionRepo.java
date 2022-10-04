package sn.youdev.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Article;
import sn.youdev.model.Section;
import sn.youdev.model.serializable.SectionId;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section, SectionId> {
    List<Section>findByArticleAndActiveTrueOrderByPositionAsc(Article article);
    Optional<Section>findByArticle_idAndPosition(Long article_id,Byte position);
}