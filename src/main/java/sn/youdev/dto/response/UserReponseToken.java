package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private Date dateExpiration = new Date();
    private  String image;
    private boolean changePassword;
//    private String email;

    public UserReponseToken(UserResponse userResponse, String token) {
        this.id = userResponse.getId();
        this.login = userResponse.getLogin();
        this.prenom = userResponse.getPrenom();
        this.nom = userResponse.getNom();
        this.token = token;
        this.roles = userResponse.getRoles();
        this.image = userResponse.getImage();
        this.changePassword = userResponse.isChangePassword();
    }
    public UserReponseToken(UserResponse userResponse, String token,Date expiration) {
        this.id = userResponse.getId();
        this.login = userResponse.getLogin();
        this.prenom = userResponse.getPrenom();
        this.nom = userResponse.getNom();
        this.token = token;
        this.roles = userResponse.getRoles();
        this.dateExpiration = expiration;
        this.image = userResponse.getImage();
        this.changePassword = userResponse.isChangePassword();
    }
}
