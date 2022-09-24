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
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe_id")
    private GroupeSanguin groupeSanguin;
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;
    private int quantity;
}
