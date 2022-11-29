package sn.youdev.model.serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sn.youdev.model.Banque;
import sn.youdev.model.GroupeSanguin;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
public class ReserveId implements Serializable {
    private Long banque_id;
    private String groupe_id;

    public ReserveId() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
