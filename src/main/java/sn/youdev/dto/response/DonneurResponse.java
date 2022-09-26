package sn.youdev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter @AllArgsConstructor @Getter
public class DonneurResponse {
    private String numeroDonneur;
    private String prenom;
    private String nom;
    private String dateDernierDon;
    private Boolean peutDonner;

    public DonneurResponse(String numeroDonneur, String prenom, String nom, Date dateDernierDon, Boolean peutDonner) {
        this.numeroDonneur = numeroDonneur;
        this.prenom = prenom;
        this.nom = nom;
        this.dateDernierDon = dateDernierDon == null ? "pas de don":dateDernierDon.toString();
        this.peutDonner = peutDonner;
    }
}
