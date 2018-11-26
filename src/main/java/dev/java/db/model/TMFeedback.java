package dev.java.db.model1;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TMFeedback extends CandidateFeedback {
    private List<FeedbackDetails> feedbackDetails = new ArrayList<>();

    @OneToMany(mappedBy = "candidateFeedback")
    public List<FeedbackDetails> getFeedbackDetails() {
        return feedbackDetails;
    }

    public void setFeedbackDetails(List<FeedbackDetails> feedbackDetails) {
        this.feedbackDetails = feedbackDetails;
    }
}
