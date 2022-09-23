package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.request.RegisterRequest;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "infos")
public class InfoPerso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String cin;
    @Column(nullable = false,unique = true)
    private String telephone;
    @OneToOne
    @JoinColumn(name = "numero_donneur")
    private Donneur numeroDonneur;
    @OneToOne(mappedBy = "infoPerso",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;
    public void setInfoPerso(RegisterRequest registerRequest){
        this.cin=registerRequest.getCin();
        this.email=registerRequest.getEmail();
        this.nom=registerRequest.getNom();
        this.prenom=registerRequest.getPrenom();
        this.telephone=registerRequest.getTelephone();
    }
}
