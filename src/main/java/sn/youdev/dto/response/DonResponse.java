package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonResponse {
    private String numero;
    private Boolean result;
    private Date date;
    private ResultatResponse resultat;
    private String banque;
    private String etat;
}
