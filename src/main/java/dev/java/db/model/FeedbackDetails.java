package dev.java.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "feedback_details", schema = "staffjobs")
public class FeedbackDetails extends AbstractEntity {
    private Requirement requirement;
    private SuitableState verifyState;
    private CandidateFeedback candidateFeedback;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "requirement", referencedColumnName = "name")
    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    @ManyToOne
    @JoinColumn(name = "verify_state", referencedColumnName = "name")
    public SuitableState getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(SuitableState verifyState) {
        this.verifyState = verifyState;
    }

    @ManyToOne
    @JoinColumn(name = "id_candidate_feedback", referencedColumnName = "id")
    public CandidateFeedback getCandidateFeedback() {
        return candidateFeedback;
    }

    public void setCandidateFeedback(CandidateFeedback devFeedback) {
        this.candidateFeedback = devFeedback;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeedbackDetails that = (FeedbackDetails) o;
        return Objects.equals(requirement, that.requirement)
               && Objects.equals(verifyState, that.verifyState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirement, verifyState);
    }
}
