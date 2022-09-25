package sn.youdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.youdev.model.Article;
import sn.youdev.model.Section;

import java.util.List;

@Repository
public interface SectionRepo extends JpaRepository<Section,Long> {
    List<Section>findByArticleAndActiveTrueOrderByPositionAsc(Article article);
}