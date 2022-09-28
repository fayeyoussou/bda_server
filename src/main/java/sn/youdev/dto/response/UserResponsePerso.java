package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponsePerso {
    private String Login;
    private String email;
    private String prenom;
    private String nom;
    private String telephone;
    private String image;
}
