package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "roles", indexes = {
        @Index(name = "idx_roles_nom", columnList = "nom",unique = true)
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private List<User> users;
}
