package sn.youdev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginChangeRequest {
    @NotNull(message = "ne doit pas être vide")
    @Length(min = 5,max = 20,message = "la taille de caractère doit être entre 5 et 20")
    @Pattern(regexp = "^(?=[a-zA-Z\\d._-]*$)(?!.*[_.]{2})[^_.].*[^_.]$",message = "ne doit contenir de caractère special  a part _ , .,-")
    private String login;
    @NotNull @NotEmpty
    private String password;
}
