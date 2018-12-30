package dev.java.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Responsibility {
    private String name;
    private List<CandidateExperience> candidateExperiences = new ArrayList<>();

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
    @JoinTable(name = "CANDIDATE_RESPONSIBILITY",
            joinColumns = {@JoinColumn(name = "RESPONSIBILITY", referencedColumnName = "NAME")},
            inverseJoinColumns = {@JoinColumn(name = "CANDIDATE_EXPERIENCE_ID", referencedColumnName = "ID")}
    )
    public List<CandidateExperience> getCandidateExperiences() {
        return candidateExperiences;
    }

    public void setCandidateExperiences(List<CandidateExperience> candidateExperiences) {
        this.candidateExperiences = candidateExperiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Responsibility that = (Responsibility) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
