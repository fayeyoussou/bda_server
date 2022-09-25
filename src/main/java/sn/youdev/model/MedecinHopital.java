package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.MedecinHopitalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "medecinshopital")
@IdClass(MedecinHopitalId.class)
public class MedecinHopital {
    @Id
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Hopital hopital;
    @Id
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;


}
