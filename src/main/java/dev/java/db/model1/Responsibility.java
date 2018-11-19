package dev.java.db.model1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Responsibility {
    private String name;
    //private Set<CandidateExperience> candidateExperiences = new HashSet<>();

    @Id
    @Column(name = "name", nullable = false, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*@ManyToMany
    @JoinTable(name = "candidate_responsibility",
            joinColumns = {@JoinColumn(name = "responsibility", referencedColumnName = "name")},
            inverseJoinColumns = {@JoinColumn(name = "id_candidate_experience", referencedColumnName = "id")}
    )
    public Set<CandidateExperience> getCandidateExperiences() {
        return candidateExperiences;
    }

    public void setCandidateExperiences(Set<CandidateExperience> candidateExperiences) {
        this.candidateExperiences = candidateExperiences;
    }*/

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
