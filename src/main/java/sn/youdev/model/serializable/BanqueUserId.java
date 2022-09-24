package sn.youdev.model.serializable;

import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Banque;
import sn.youdev.model.User;

import java.io.Serializable;

@Getter @Setter
public class BanqueUserId implements Serializable {
    private Banque banque;
    private User user;
}
