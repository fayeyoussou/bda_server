package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserReponseToken  {
    private String token;
    private Long id;
    private String login;
    private String prenom;
    private String nom;
    private List<String> roles;
//    private String email;

    public UserReponseToken(UserResponse userResponse, String token) {
        this.id = userResponse.getId();
        this.login = userResponse.getLogin();
        this.prenom = userResponse.getPrenom();
        this.nom = userResponse.getNom();
        this.token = token;
        this.roles = userResponse.getRoles();
    }
}
