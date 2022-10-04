package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.SectionId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sections")
@IdClass(SectionId.class)
public class Section {
    @Id
    @Column(name = "position")
    private Byte position;
    @Id
    @Column(name = "article_id")
    private Long article_id;
    private Boolean active = false;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String contenu;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id",nullable = false)
    @MapsId
    private Article article;
    private Boolean etat = true;

    /**
     *
     * @param position
     * @param title
     * @param contenu
     * @param article
     */
    public Section(Byte position, String title, String contenu, Article article) {
        this.position = position;
        this.title = title;
        this.contenu = contenu;
        this.article = article;
        this.article_id = article.getId();
    }
}
