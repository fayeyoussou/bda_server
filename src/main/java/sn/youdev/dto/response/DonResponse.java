package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonResponse {
    private String numero;
    private Boolean result;
    private String date;
    private String resultat;
    private String banque;
}
