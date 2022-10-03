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

    public MedecinHopitalId() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
