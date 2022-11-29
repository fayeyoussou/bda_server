package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import sn.youdev.config.Constante;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 60)
    private String code;
    @Column(nullable = false)
    private Date expiration;
    /**
     * type 0 = register validation
     * type 1 = access token
     * type 2 = reset password
     */
    @Column(nullable = false)
    private Byte type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;



    public Token(Byte type, User user) {
        super();
        this.type = type;
        this.user = user;
        this.expiration = Constante.calculateExp(this.type == 1 ? 60*24*30 : 10);
        this.code = RandomStringUtils.randomAlphanumeric(60).toLowerCase();
    }
    public Token(User user) {
        super();
        this.type = 0;
        this.user = user;
        this.expiration = Constante.calculateExp( 10);
        this.code = RandomStringUtils.randomAlphabetic(6).toUpperCase();
    }

    public Token(byte type, User user, int i) {
        this.type = type;
        this.user = user;
        this.expiration = Constante.calculateExp(60*24*i);
        this.code = RandomStringUtils.randomAlphanumeric(60).toLowerCase();
    }
}
