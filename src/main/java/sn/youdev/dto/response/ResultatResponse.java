package sn.youdev.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultatResponse {
    private String numeroDonneur;
    private String nomDonneur;
    private String hepatiteB;
    private String hepatiteC;
    private String vih1;
    private String vih2;
    private String siphylis;
    private String natTest;

    private String getStringResult(Boolean result){
        return result? "positif":"négatif";
    }
    public ResultatResponse(String numeroDonneur, String nomDonneur, Boolean hepatiteB, Boolean hepatiteC, Boolean vih1, Boolean vih2, Boolean siphylis, String natTest) {
        this.numeroDonneur = numeroDonneur;
        this.nomDonneur = nomDonneur;
        this.hepatiteB = getStringResult(hepatiteB);
        this.hepatiteC = getStringResult(hepatiteC);
        this.vih1 = getStringResult(vih1);
        this.vih2 = getStringResult(vih2);
        this.siphylis = getStringResult(siphylis);
        this.natTest = natTest;
    }
}
