package sn.youdev.dto.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
@Getter
public class DonneurRequest {
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
    @Length(max = 3,min = 1)
    private String groupe;
    }
