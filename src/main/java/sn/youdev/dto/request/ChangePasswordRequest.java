package sn.youdev.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class ChangePasswordRequest {

    @NotEmpty
    @NotBlank
    private String oldPassword;
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message = "format mot de passe invalide ")
    @Pattern(regexp = ".*[0-9].*",message = "doit contenir au moins 1 chiffre")
    @Pattern(regexp = ".*[a-z].*",message = "doit contenir une minuscule")
    @Pattern(regexp = ".*[A-Z].*",message = "doit contenir une majuscule")
    @Pattern(regexp = "[\\w\\W]{8,}",message = "au moins 8 caractères")
    @Pattern(regexp = "[\\w\\W]{0,20}",message = "au plus 20 caractères")
    @Pattern(regexp = "^[\\w\\W]+$" , message = "les caractères spéciales non autorise")
    @Pattern(regexp = "^(?!.*__.*)(?!.*--.*)(?!.*-_.*)(?!.*_-.*)[a-zA-Z][a-zA-Z0-9_-]+$" , message = "deux tiré successif non autorise")
    private String newPassword;
    @NotEmpty
    @NotBlank
    private String confirmation;
}
