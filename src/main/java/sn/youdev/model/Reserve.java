package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.ReserveId;

import javax.persistence.*;

@Entity
@Table(name = "reserves")
@Getter @Setter @NoArgsConstructor
@IdClass(ReserveId.class)
public class Reserve {
    @Id
    @Column(name = "groupe_id")
    private String groupe_id;
    @Id
    @Column(name = "banque_id")
    private Long banque_id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe_id")
    @MapsId
    private GroupeSanguin groupeSanguin;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    @MapsId
    private Banque banque;
    private int quantity=0;

    public Reserve(GroupeSanguin groupeSanguin, Banque banque) {
        this.groupeSanguin = groupeSanguin;
        this.banque = banque;
        this.banque_id = banque.getId();
        this.groupe_id = groupeSanguin.getGroupe();
    }
}
