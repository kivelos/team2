package dev.java.db.model;

import java.util.Objects;

public class CandidateState extends Entity {
    private String name;

    public CandidateState() {
    }

    public CandidateState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateState that = (CandidateState) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CandidateState{" +
                "name='" + name + '\'' +
                '}';
    }
}
