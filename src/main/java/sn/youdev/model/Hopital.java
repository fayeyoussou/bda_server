package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String adresse;
    private String localisation;
    private String telephone;
    @OneToMany(mappedBy = "hopital",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<MedecinHopital> medecins;
}
