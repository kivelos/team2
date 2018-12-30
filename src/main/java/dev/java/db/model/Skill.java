package dev.java.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SKILL", schema = "team6")
public class Skill {
    private String name;
    private List<Candidate> candidates = new ArrayList<>();

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "NAME", nullable = false, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "CANDIDATE_COMPETENCE",
            joinColumns = {@JoinColumn(name = "SKILL", referencedColumnName = "NAME")},
            inverseJoinColumns = {@JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")}
    )
    @JsonIgnore
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Skill{"
               + "name='" + name + '\''
               + ", candidates=" + candidates
               + '}';
    }
}
