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
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;
    @Id
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
