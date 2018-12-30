package dev.java.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SUITABLE_STATE", schema = "team6")
public class SuitableState {
    private String name;
    private List<FeedbackDetails> feedbackDetails = new ArrayList<>();

    @Id
    @Column(name = "NAME", nullable = false, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToMany(mappedBy = "verifyState")
    @JsonIgnore
    public List<FeedbackDetails> getFeedbackDetails() {
        return feedbackDetails;
    }

    public void setFeedbackDetails(List<FeedbackDetails> feedbackDetails) {
        this.feedbackDetails = feedbackDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuitableState that = (SuitableState) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
