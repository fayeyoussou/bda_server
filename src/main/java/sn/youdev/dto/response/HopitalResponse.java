package sn.youdev.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class HopitalResponse {
    private Long id;
    private String nom;
    private String Adresse;
    private String position;
    private int employees;
    private String telephone;
}
