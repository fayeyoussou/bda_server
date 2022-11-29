package sn.youdev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.parameters.P;
import sn.youdev.model.serializable.EtatDon;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

import static sn.youdev.config.Constante.PAS_VIDE;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DonRequest {
    @NotNull(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE) @Length(min = 8)
    private String donneur;
    private String observation;
    private Long banque;
    @PastOrPresent @NotNull(message = PAS_VIDE)
    private Date date;
}
