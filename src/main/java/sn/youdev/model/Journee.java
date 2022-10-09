package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.ArticleResponseList;
import sn.youdev.dto.response.JourneeResponse;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "journees")
public class Journee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_autorisation;
    private Date date_journee;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private Article article;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentaire_organisateur;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentaire_cnts;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "organisateur")
    private User organisateur;
    @OneToMany(mappedBy = "journee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<JourneeDon> dons;

    public Journee(Date date_autorisation, Date date_journee, String commentaire_cnts, User organisateur) {
        this.date_autorisation = date_autorisation;
        this.date_journee = date_journee;
        this.commentaire_cnts = commentaire_cnts;
        this.organisateur = organisateur;
    }
    public JourneeResponse response(){
        InfoPerso infos = this.organisateur.getInfoPerso();
        String nom = infos.getPrenom()+" "+infos.getNom();
        String dons;
        if(this.dons==null || this.dons.size()==0)dons = "pas de don pour le moment";
        else dons = this.dons.size()+" dons effectue";
        ArticleResponseList responseList = this.article == null ? null : this.article.response().liste();
        return new JourneeResponse(
                this.date_journee,
                this.date_autorisation,
                responseList,
                nom,
                this.commentaire_organisateur,
                this.commentaire_cnts,
                dons
        );
    }
}
