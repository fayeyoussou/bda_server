package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.ResultatResponse;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "resultat")
public class Resultat {
    @Id
    @Column(name = "numero")
    private String numero;
    @OneToOne(optional = false,fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "numero")
    private Don don;
    private Boolean hepatiteB;
    private Boolean hepatiteC;
    private Boolean vih1;
    private Boolean vih2;
    private Boolean syphillis;
    private String natTest;
    public ResultatResponse resultatResponse(){
        Donneur donneur =this.don.getDonneur();
        InfoPerso infos = this.don.getDonneur().getInfoPerso();
        String nom = infos.getPrenom()+" "+infos.getNom();
        return new ResultatResponse(donneur.getNumero(),nom,this.hepatiteB,this.hepatiteC,this.vih1,this.vih2,this.syphillis,this.natTest);
    }
}
