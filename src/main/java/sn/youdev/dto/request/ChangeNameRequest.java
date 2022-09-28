package sn.youdev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeNameRequest {
    @NotEmpty
    @NotBlank
    private String prenom;
    @NotEmpty
    @NotBlank
    private String nom;

}
