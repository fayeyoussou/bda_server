package sn.youdev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "ne doit pas être vide")
    @Length(min = 5,max = 20,message = "la taille de caractère doit être entre 5 et 20")
    @Pattern(regexp = "^(?=[a-zA-Z\\d._-]*$)(?!.*[_.]{2})[^_.].*[^_.]$",message = "ne doit contenir de caractère special  a part _ , .,-")
    private String login;
    @NotBlank(message = "ne doit pas être vide") @NotEmpty(message = "ne doit pas être vide")
    @Pattern(regexp = ".*[0-9].*",message = "doit contenir au moins 1 chiffre")
    @Pattern(regexp = ".*[a-z].*",message = "doit contenir une minuscule")
    @Pattern(regexp = ".*[A-Z].*",message = "doit contenir une majuscule")
    @Pattern(regexp = "[\\w\\W]{8,}",message = "au moins 8 caractères")
    @Pattern(regexp = "[\\w\\W]{0,20}",message = "au plus 20 caractères")
    @Pattern(regexp = "^[\\w\\W]+$" , message = "les caractères spéciales non autorise")
    @Pattern(regexp = "^(?!.*__.*)[a-zA-Z]\\w+$" , message = "deux underscore successif ou au debut non autorise")
    private String password;
}
