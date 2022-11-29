package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.BanqueResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity @Getter @Setter @NoArgsConstructor @Table(name = "banques")
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;
    @Column(unique = true)
    private String telephone;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "localisation_id")
    private Localisation localisation;
    @OneToMany(mappedBy = "banque",fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    private List<Don> dons;
    @OneToMany(mappedBy = "banque",fetch = FetchType.LAZY)
    private List<BanqueUser> banqueUsers;
    @OneToMany(mappedBy = "banque",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Reserve> reserves = new ArrayList<>();
    private Boolean etat = true;
    public BanqueResponse response(){
        BanqueResponse response = new BanqueResponse();
        String adresse = this.localisation.getAdresse();
        response.setAdresse(adresse==null ? "adresse non fournit": adresse);
        response.setNom(this.nom);
        response.setPosition(this.localisation.getLatitude()+"/"+this.localisation.getLongitude());
        response.setReserves(getReserve());
        response.setTelephone(this.telephone);
        response.setId(this.id);
        return response;
    }
    public Map<String,Integer> getReserve(){
        Map<String, Integer> reserve = new HashMap<>();
        for (Reserve rs:this.reserves
             ) {
            reserve.put(rs.getGroupeSanguin().getGroupe(),rs.getQuantity());
        }
        return reserve;
    }

}
