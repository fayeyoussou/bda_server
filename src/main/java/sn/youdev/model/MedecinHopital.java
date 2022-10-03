package sn.youdev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.MedecinHopitalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medecinshopital")
@IdClass(MedecinHopitalId.class)
public class MedecinHopital {
    @Id
    @Column(name = "hopital_id")
    private Long hopital_id;
    @Id
    @Column(name = "user_id")
    private Long user_id;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "hopital_id")
    @MapsId
    private Hopital hopital;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    public MedecinHopital(Hopital hopital, User user) {
        this.hopital_id = hopital.getId();
        this.user_id = user.getId();
        this.hopital = hopital;
        this.user = user;
    }
}
