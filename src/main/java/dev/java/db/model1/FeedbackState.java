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
@Table(name = "feedback_state", schema = "staffjobs")
public class FeedbackState {
    private String name;
    private Set<CandidateFeedback> candidatesFeedbacks = new HashSet<>();

    @Id
    @Column(name = "name", nullable = false)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "feedbackState")
    @JsonIgnore
    public Set<CandidateFeedback> getCandidatesFeedbacks() {
        return candidatesFeedbacks;
    }

    public void setCandidatesFeedbacks(Set<CandidateFeedback> candidatesFeedbacks) {
        this.candidatesFeedbacks = candidatesFeedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeedbackState that = (FeedbackState) o;
        return Objects.equals(name, that.name)
                && Objects.equals(candidatesFeedbacks, that.candidatesFeedbacks);
    }

    @Override
    public String toString() {
        return "FeedbackState{"
                + "name='" + name + '\''
                + ", candidates=" + candidatesFeedbacks
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, candidatesFeedbacks);
    }
}
