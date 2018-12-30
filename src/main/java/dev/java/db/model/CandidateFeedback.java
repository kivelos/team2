package dev.java.db.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CANDIDATE_FEEDBACK", schema = "team6")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CandidateFeedback extends AbstractEntity {
    private String feedbackText;
    private Candidate candidate;
    private FeedbackState feedbackState;
    private User interviewer;

    @Id
    @Column(name = "ID", nullable = false)
    public long getId() {
        return super.getId();
    }

    @Basic
    @Column(name = "FEEDBACK_TEXT", length = 4000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    @ManyToOne
    @JoinColumn(name = "INTERVIEWER_ID", referencedColumnName = "ID")
    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User user) {
        this.interviewer = user;
    }

    @ManyToOne
    @JoinColumn(name = "FEEDBACK_STATE", referencedColumnName = "NAME")
    public FeedbackState getFeedbackState() {
        return feedbackState;
    }

    public void setFeedbackState(FeedbackState feedbackState) {
        this.feedbackState = feedbackState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateFeedback that = (CandidateFeedback) o;
        return Objects.equals(feedbackText, that.feedbackText)
               && Objects.equals(feedbackState, that.feedbackState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackText, feedbackState);
    }
}
