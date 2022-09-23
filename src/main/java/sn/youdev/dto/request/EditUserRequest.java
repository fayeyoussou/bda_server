package sn.youdev.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class EditUserRequest {
    @NotEmpty
    @NotBlank
    private String prenom;
    @NotEmpty
    @NotBlank
    private String nom;
    @Length(min = 5,max = 20,message = "la taille de caractère doit être entre 5 et 20")
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{5,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")
    private String login;
    private String password;
}
