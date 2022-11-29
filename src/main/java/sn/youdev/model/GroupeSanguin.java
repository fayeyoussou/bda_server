package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "groupe_sanguins")
@Getter @Setter @NoArgsConstructor
public class GroupeSanguin {
    @Id
    private String groupe;
    @OneToMany(mappedBy = "groupeSanguin")
    private List<Donneur> donneurs;

    public GroupeSanguin(String groupe) {
        this.groupe = groupe;
    }
}
