package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @Table(name = "banques")
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nom;
    private String adresse;
    @Column(unique = true)
    private String telephone;
    private String localisation;
    @OneToMany(mappedBy = "banque")
    private List<Don> dons;
    @OneToMany(mappedBy = "banque")
    private List<BanqueUser> banqueUsers;
}
