package sn.youdev.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @NoArgsConstructor
public class BanqueRequete {
    @NotNull(message = "ne doit pas etre vide") @NotEmpty(message = "ne doit pas etre vide")
    private String nom;
    @NotNull(message = "ne doit pas etre vide")
    private Long localisation;
    @NotNull(message = "ne doit pas être vide")
    @Pattern(regexp = "^(?!.*  .*)[\\d+() ]{9,20}$", message = "ne doit contenir que des chiffres , des parentheses et plus")
    @Length(min = 9,max = 20)
    private String telephone;
}
