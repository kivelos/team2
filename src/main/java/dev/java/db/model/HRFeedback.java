package dev.java.db.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HRFeedback extends CandidateFeedback {
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "id_interview", referencedColumnName = "id")
    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
