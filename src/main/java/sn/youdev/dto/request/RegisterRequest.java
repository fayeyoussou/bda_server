package sn.youdev.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;


@Getter
public class RegisterRequest {
    @Email(message = "format e-mail non valide") @NotNull(message = "ne doit pas être vide")
    private String email;
    @NotEmpty(message = "ne doit pas être vide")
    @NotBlank(message = "ne doit pas être vide")
    @NotNull(message = "ne doit pas être vide")
    private String prenom;
    @NotEmpty(message = "ne doit pas être vide")
    @NotBlank(message = "ne doit pas être vide")
    @NotNull(message = "ne doit pas être vide")
    private String nom;
    @Length(min = 13,max = 13 ,message = "doit contenir exactement 13 chiffres")
    @NotNull(message = "ne doit pas être vide")
    @Pattern(regexp = "^\\d*$", message = "ne doit contenir que des chiffres")
    @Pattern(regexp = "^[12]\\w*$", message = "doit commencer par 1 ou 2")
    private String cin;
    @NotNull(message = "ne doit pas être vide")
    @Pattern(regexp = "^(?!.*  .*)[\\d+() ]{9,20}$", message = "ne doit contenir que des chiffres , des parentheses et plus")
    @Length(min = 9,max = 20)
    private String telephone;
    @NotNull(message = "ne doit pas être vide")
    @Length(min = 5,max = 20,message = "la taille de caractère doit être entre 5 et 20")
    @Pattern(regexp = "^(?=[a-zA-Z\\d._-]*$)(?!.*[_.]{2})[^_.].*[^_.]$",message = "ne doit contenir de caractère special  a part _ , .,-")
    private String login;
    @NotBlank(message = "ne doit pas être vide") @NotEmpty(message = "ne doit pas être vide")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",message = "format mot de passe invalide")
//    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{5,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message = "format mot de passe invalide")
    @Pattern(regexp = ".*[0-9].*",message = "doit contenir au moins 1 chiffre")
    @Pattern(regexp = ".*[a-z].*",message = "doit contenir une minuscule")
    @Pattern(regexp = ".*[A-Z].*",message = "doit contenir une majuscule")
    @Pattern(regexp = "[\\w\\W]{8,}",message = "au moins 8 caractères")
    @Pattern(regexp = "[\\w\\W]{0,20}",message = "au plus 20 caractères")
    @Pattern(regexp = "^[\\w\\W]+$" , message = "les caractères spéciales non autorise")
    @Pattern(regexp = "^(?!.*__.*)[a-zA-Z]\\w+$" , message = "deux underscore successif ou au debut non autorise")
    private String password;
    @NotBlank(message = "ne doit pas être vide") @NotEmpty(message = "ne doit pas être vide") @NotNull(message = "ne doit pas être vide")
    private String confirmation;

}
