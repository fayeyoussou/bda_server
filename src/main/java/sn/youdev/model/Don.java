package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dons")
public class Don {
    @Id
    private String numero;
    private String observation;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "numero_donneur",nullable = false)
    private Donneur donneur;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;
    private Date date;
    @OneToOne(mappedBy = "don",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Resultat resultat;
}
