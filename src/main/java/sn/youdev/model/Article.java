package sn.youdev.model;

import lombok.Data;
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
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titre;
    private String description;
    private TypeArticle type;
    private int vues = 0;
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
        String nom = infoPerso ==null ? "" :infoPerso.getPrenom()+" "+infoPerso.getNom();
        articleResponse.setAuteur(nom);
        articleResponse.setDate_modification(this.dateModification);
        articleResponse.setDate_publication(this.datePublication);
        articleResponse.setImage(this.image ==null ? "not found":this.image.getNom());
        articleResponse.setDescription_image(this.image ==null || this.image.getDescription() ==null? "no description":this.image.getDescription());
        articleResponse.setTitre(this.titre);
        articleResponse.setDescription(description);
        articleResponse.setVues(vues);
        assert infoPerso != null;
        articleResponse.setAuteurImage(infoPerso.getImage().getNom());
        List<SectionString> sectionStrings = new ArrayList<>();
        if(this.sections!=null) {
            for (Section section : this.sections
            ) {
                sectionStrings.add(new SectionString(section.getPosition(), section.getTitle(), section.getContenu()));
            }
        }
        articleResponse.setSections(sectionStrings);
        articleResponse.setId(this.id);
        articleResponse.setType(this.type.name().toLowerCase());
        return articleResponse;
    }
}
