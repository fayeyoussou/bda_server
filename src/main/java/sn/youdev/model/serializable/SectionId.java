package sn.youdev.model.serializable;

import java.io.Serializable;

public class SectionId implements Serializable {
    private Long article_id;
    private Byte position;

    public SectionId() {
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
