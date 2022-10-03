package sn.youdev.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static sn.youdev.config.Constante.PAS_VIDE;

@Getter @Setter
public class HopitalRequest {
    @Length(min = 4,max = 100)
    @NotNull(message = PAS_VIDE)
    private String nom;
    @NotNull(message = PAS_VIDE)
    private Long localisation;
    @NotNull(message = PAS_VIDE)
    @Pattern(regexp = "^(?!.*  .*)[\\d+() ]{9,20}$", message = "ne doit contenir que des chiffres , des parentheses et plus")
    @Length(min = 9,max = 20)
    private String telephone;
}
