package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import sn.youdev.dto.response.ArticleResponse;
import sn.youdev.dto.response.SectionString;
import sn.youdev.model.serializable.TypeArticle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String titre;
    private TypeArticle type;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(nullable = false,name = "auteur")
    private User auteur;
    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @Fetch(value = FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Section> sections;
    private Boolean valide = false;
    private Boolean disponible = true;
    private Date datePublication;
    private Date dateModification;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "image")
    private File image;
    public ArticleResponse response(){
        ArticleResponse articleResponse = new ArticleResponse();
        InfoPerso infoPerso = this.auteur.getInfoPerso();
        String nom = infoPerso.getPrenom()+" "+infoPerso.getNom();
        articleResponse.setAuteur(nom);
        articleResponse.setDate_modification(this.dateModification);
        articleResponse.setDate_publication(this.datePublication);
        articleResponse.setImage(this.image.getNom());
        articleResponse.setDescription_image(this.image.getDescription());
        List<SectionString> sectionStrings = new ArrayList<>();
        for (Section section:this.sections
             ) {
            sectionStrings.add(new SectionString(section.getPosition(),section.getTitle(),section.getContenu()));
        }
        articleResponse.setSections(sectionStrings);
        articleResponse.setId(this.id);
        articleResponse.setType(this.type.name().toLowerCase());
        return articleResponse;
    }
}
