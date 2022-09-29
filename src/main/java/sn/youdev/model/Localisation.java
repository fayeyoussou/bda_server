package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "localisations")
public class Localisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String code;
    private String adresse;
    @Column(nullable = false)
    private Float longitude;
    @Column(nullable = false)
    private Float latitude;
    public Map<String, String> getResponse(){
        Map <String, String> map = new HashMap<String, String>();
        map.put("id",this.id.toString());
        map.put("code",code);
        map.put("adresse",adresse != null ? adresse : "pas d'adresse fournit");
        map.put("longitude",longitude.toString());
        map.put("latitude",latitude.toString());
        return map;
    }
}
