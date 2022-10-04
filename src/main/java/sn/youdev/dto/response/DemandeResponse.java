package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor
public class DemandeResponse {
    private Long id;
    private String objet;
    private String message;
    private Date date;
    private String demandeur;
    private String reponse;
}
