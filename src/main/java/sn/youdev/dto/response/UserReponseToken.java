package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReponseToken  {
    private String token;
    private Long id;
    private String login;
    private String nom;

    public UserReponseToken(UserResponse userResponse, String token) {
        this.id = userResponse.getId();
        this.login = userResponse.getLogin();
        this.nom = userResponse.getNom();
        this.token = token;
    }
}
