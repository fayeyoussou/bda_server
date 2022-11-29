package sn.youdev.model.serializable;

import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Banque;
import sn.youdev.model.User;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class BanqueUserId implements Serializable {
    private Long banque_id;
    private Long user_id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BanqueUserId() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
