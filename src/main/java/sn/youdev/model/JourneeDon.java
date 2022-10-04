package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.JourneeDonId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "journee_don")
@IdClass(JourneeDonId.class)
public class JourneeDon {
    @Id
    @Column(name = "numero_don")
    private String numero_don;
    @Id
    @Column(name = "journee_id")
    private Long journee_id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "journee_id")
    @MapsId
    private Journee journee;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "numero_don")
    @MapsId
    private Don don;

    public JourneeDon(Journee journee, Don don) {
        this.journee_id = journee.getId();
        this.numero_don = don.getNumero();
        this.journee = journee;
        this.don = don;
    }
}
