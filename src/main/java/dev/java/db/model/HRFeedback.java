package dev.java.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HRFeedback extends CandidateFeedback {
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "INTERVIEW_ID", referencedColumnName = "ID")
    @JsonIgnore
    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
