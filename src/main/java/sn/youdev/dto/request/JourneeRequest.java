package sn.youdev.dto.request;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class JourneeRequest {
    private Date dateAutorisation;
    private Date dateJournee;
    private Long organisateur;
}
