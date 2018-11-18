package dev.java.db.model1;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "candidate_state", schema = "staffjobs")
public class CandidateState {
    private String name;
    private Set<Candidate> candidates = new HashSet<>();

    @Id
    @Column(name = "name", nullable = false)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "candidateState")
    @JsonIgnore
    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<Candidate> candidates) {
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
        CandidateState that = (CandidateState) o;
        return Objects.equals(name, that.name)
               && Objects.equals(candidates, that.candidates);
    }

    @Override
    public String toString() {
        return "CandidateState{"
               + "name='" + name + '\''
               + ", candidates=" + candidates
               + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, candidates);
    }
}
