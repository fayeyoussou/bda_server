package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.DemandeListResponse;
import sn.youdev.dto.response.DemandeResponse;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "demandes")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String objet;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String message;
    private Date date = new Date();
    private Boolean etat = true;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeur",nullable = false)
    private User demandeur;
    @OneToOne(mappedBy = "demande")
    @JoinColumn(name = "reponse_id")
    private Reponse reponse;
    public DemandeResponse response(){
        InfoPerso infos = this.demandeur.getInfoPerso();
        String demandeur = infos.getPrenom()+" "+infos.getNom();
        return new DemandeResponse(
                this.id,
                this.objet,
                this.message,
                this.date,
                demandeur,
                this.reponse ==null ? "pas encore de réponse":this.reponse.getMessage()
        );
    }
    public DemandeListResponse responseList(){
        InfoPerso infos = this.demandeur.getInfoPerso();
        String demandeur = infos.getPrenom()+" "+infos.getNom();
        return new DemandeListResponse(
                this.id,
                this.objet,
                this.date,
                demandeur,
                this.reponse != null
        );
    }
}
