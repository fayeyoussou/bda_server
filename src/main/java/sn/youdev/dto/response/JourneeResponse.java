package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor
public class JourneeResponse {
    private Date dateJournee;
    private Date dateAutorisation;
    private ArticleResponseList article;
    private String organisateur;
    private String commentaireOrganisateur;
    private String commentaireCnts;
    private String dons;
}
