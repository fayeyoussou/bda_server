package sn.youdev.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "groupe_sanguins")
public class GroupeSanguin {
    @Id
    private String groupe;
    @OneToMany(mappedBy = "groupeSanguin")
    private List<Donneur> donneurs;
}
