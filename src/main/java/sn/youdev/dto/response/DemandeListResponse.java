package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter @Setter @AllArgsConstructor
public class DemandeListResponse {
    private Long id;
    private String objet;
    private Date date;
    private String demandeur;
    private Boolean reponse;
}
