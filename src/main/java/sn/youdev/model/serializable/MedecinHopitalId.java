package sn.youdev.model.serializable;

import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Hopital;
import sn.youdev.model.User;

import java.io.Serializable;
@Getter @Setter
public class MedecinHopitalId implements Serializable {
    private User user;
    private Hopital hopital;
}
