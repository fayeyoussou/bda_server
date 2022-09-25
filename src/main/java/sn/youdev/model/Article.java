package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import sn.youdev.model.serializable.TypeArticle;

import javax.persistence.*;
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
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(nullable = false,name = "autheur")
    private User autheur;
    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @Fetch(value = FetchMode.SUBSELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Section> sections;
    private Boolean valide = false;
}
