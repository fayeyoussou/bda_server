package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.dto.response.DonResponse;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dons")
public class Don {
    @Id
    private String numero;
    private String observation;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "numero_donneur",nullable = false)
    private Donneur donneur;
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name = "banque_id")
    private Banque banque;
    private Date date;
    @OneToOne(mappedBy = "don",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Resultat resultat;
    public DonResponse getDonResponse(){
        DonResponse donResponse = new DonResponse();
        donResponse.setBanque(this.banque.getNom());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        donResponse.setDate(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+ calendar.get(Calendar.YEAR));
        donResponse.setNumero(this.numero);
        donResponse.setResult(this.resultat != null);
        return donResponse;
    }
}
