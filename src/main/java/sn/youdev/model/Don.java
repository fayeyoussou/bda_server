package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.youdev.config.Constante;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.model.serializable.EtatDon;

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
    private Date date = new Date();
    private EtatDon etat = EtatDon.DISPONIBLE;
    @OneToOne(mappedBy = "don",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Resultat resultat;

    public Don(String observation, Donneur donneur, Banque banque, Date date) {
        this.observation = observation;
        this.donneur = donneur;
        this.banque = banque;
        this.date = date;
    }

    public Don(String observation, Donneur donneur, Banque banque) {
        this.observation = observation;
        this.donneur = donneur;
        this.banque = banque;
        this.numero = Constante.generateNumero("DN");
    }

    public DonResponse getDonResponse(){
        DonResponse donResponse = new DonResponse();
        donResponse.setBanque(this.banque.getNom());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        donResponse.setDate(calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+ calendar.get(Calendar.YEAR));
        donResponse.setNumero(this.numero);
        donResponse.setResult(this.resultat != null);
        donResponse.setEtat(this.etat.name());
        return donResponse;
    }
}
