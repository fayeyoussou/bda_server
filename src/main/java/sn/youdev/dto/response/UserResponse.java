package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String login;
    private String nom;

}
