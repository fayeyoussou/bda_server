package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.model.serializable.BanqueUserId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "banque_users")
@IdClass(BanqueUserId.class)
public class BanqueUser {
    @Id
    @Column(name = "banque_id")
    private Long banque_id;
    @Id
    @Column(name = "user_id")
    private Long user_id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "banque_id")
    private Banque banque;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public BanqueUser(Banque banque, User user) {
        this.banque_id = banque.getId();
        this.user_id = user.getId();
        this.banque = banque;
        this.user = user;
    }
}
