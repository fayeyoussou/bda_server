package sn.youdev.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @NoArgsConstructor
public class BanqueResponse {
    private Long id;
    private String nom;
    private String telephone;
    private String adresse;
    private String position;
    private Map<String,Integer> reserves;
}
