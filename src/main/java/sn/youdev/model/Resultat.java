package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean Vih1;
    private Boolean Vih2;
    private Boolean syphillis;
    private String natTest;
}
