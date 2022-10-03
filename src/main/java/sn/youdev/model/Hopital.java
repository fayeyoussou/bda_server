package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.HopitalResponse;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "hopitaux")
public class Hopital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "localisation_id")
    private Localisation localisation;
    private String telephone;
    private Boolean etat = true;
    @OneToMany(mappedBy = "hopital",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MedecinHopital> medecins;
    public HopitalResponse hopitalResponse(){
        HopitalResponse response = new HopitalResponse();
        String adresse = this.localisation.getAdresse();
        response.setAdresse(adresse==null ? "adresse non fournit": adresse);
        response.setPosition(this.localisation.getLatitude()+"/"+this.localisation.getLongitude());
        response.setNom(this.nom);
        response.setId(this.id);
        response.setTelephone(this.telephone);
        response.setEmployees(medecins==null ? 0 : medecins.size());
        return response;
    }
}
