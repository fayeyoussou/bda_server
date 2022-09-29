package sn.youdev.model.serializable;

import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Banque;
import sn.youdev.model.GroupeSanguin;

import java.io.Serializable;

@Getter @Setter
public class ReserveId implements Serializable {
    private Long banque_id;
    private String groupe_id;
}
