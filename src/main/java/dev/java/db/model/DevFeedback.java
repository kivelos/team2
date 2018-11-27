package dev.java.db.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DevFeedback extends CandidateFeedback {
    private Interview interview;
    private List<FeedbackDetails> feedbackDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_interview", referencedColumnName = "id")
    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    @OneToMany(mappedBy = "candidateFeedback")
    public List<FeedbackDetails> getFeedbackDetails() {
        return feedbackDetails;
    }

    public void setFeedbackDetails(List<FeedbackDetails> feedbackDetails) {
        this.feedbackDetails = feedbackDetails;
    }
}
