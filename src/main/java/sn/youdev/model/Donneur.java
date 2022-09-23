package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor @Entity @Table(name = "donneurs")
public class Donneur {
    @Id
    private String numero;
    @OneToOne(mappedBy = "numeroDonneur")
    private InfoPerso infoPerso;
}
