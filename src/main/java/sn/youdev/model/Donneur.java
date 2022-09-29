package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import sn.youdev.config.Constante;
import sn.youdev.dto.response.DonneurResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @Entity @Table(name = "donneurs")
public class Donneur {
    @Id
    private String numero;
    @OneToOne(mappedBy = "numeroDonneur",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private InfoPerso infoPerso;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "groupe")
    private GroupeSanguin groupeSanguin;
    @OneToMany(mappedBy = "donneur",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Don> dons = new ArrayList<>();
    private Boolean active =  true;

    public Donneur(InfoPerso infoPerso, GroupeSanguin groupeSanguin, List<Don> dons) {
        this.infoPerso = infoPerso;
        this.groupeSanguin = groupeSanguin;
        this.dons = dons;
    }
    public boolean getPeutDonner(){
        Date dateDon = getDateDernierDon();
        if(dateDon == null) return true;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MONTH,-3);
        return dateDon.before(calendar.getTime());

    }
    public Date getDateDernierDon(){

        if(this.dons==null || this.dons.isEmpty()) return null;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(this.dons.get(0).getDate());
        for(Don don : this.dons){
            Calendar cal = Calendar.getInstance();
            cal.setTime(don.getDate());
            if(cal.after(calendar2)) calendar2.setTime(don.getDate());
        }
        return calendar2.getTime();
    }
    public DonneurResponse getDonneurResponse(){
        return new DonneurResponse(this.numero,this.infoPerso.getPrenom(),this.infoPerso.getNom(),this.getDateDernierDon(),this.getPeutDonner(),this.dons==null ? 0 :this.dons.size());
    }
    public Donneur generateNumero(){
        this.numero = Constante.generateNumero("DO");

//        this.numero = calendar.getRandomStringUtils.randomAlphanumeric(6);
        return this;
    }





}
