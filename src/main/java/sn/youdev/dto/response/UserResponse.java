package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String login;
    private String prenom;
    private String nom;
    private List<String> roles;
//    private String email;

}
