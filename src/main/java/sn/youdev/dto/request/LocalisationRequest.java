package sn.youdev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LocalisationRequest {
    @NotNull @NotEmpty @Length(min = 3,max = 100)
    private String code;
    private String adresse;
    @NotNull @Digits(integer = 3, fraction = 2)
    private Float longitude;
    @NotNull @Digits(integer = 3, fraction = 2)
    private Float latitude;
}
