package dev.java.db.model1;

import javax.persistence.Embeddable;

@Embeddable
public abstract class AbstractEntity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
