package sn.youdev.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static sn.youdev.config.Constante.PAS_VIDE;

@Getter @Setter
public class ResultatRequest {
    @NotNull(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String Don;
    @NotNull(message = PAS_VIDE)
    private Boolean vih1;
    @NotNull(message = PAS_VIDE)
    private Boolean vih2;
    @NotNull(message = PAS_VIDE)
    private Boolean hepatiteB;
    @NotNull(message = PAS_VIDE)
    private Boolean hepatiteC;
    @NotNull(message = PAS_VIDE)
    private Boolean syph;
    @NotNull(message = PAS_VIDE) @NotEmpty(message = PAS_VIDE)
    private String nat;
}
