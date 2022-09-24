package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity @Table(name = "donneurs")
public class Donneur {
    @Id
    private String numero;
    @OneToOne(mappedBy = "numeroDonneur")
    private InfoPerso infoPerso;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe")
    private GroupeSanguin groupeSanguin;
    @OneToMany(mappedBy = "donneur")
    private List<Don> dons;


}
