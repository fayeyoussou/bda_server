package sn.youdev.model.serializable;

import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Banque;
import sn.youdev.model.User;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class BanqueUserId implements Serializable {
    private Banque banque;
    private User user;

    @Override
    public boolean equals(Object obj) {
        BanqueUserId  ban = (BanqueUserId) obj;
        return Objects.equals(this.banque.getId(), ban.getBanque().getId()) && Objects.equals(this.user.getId(), ban.getUser().getId());
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
