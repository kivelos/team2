package dev.java.db.model;

import java.util.Objects;

public class InterviewFeedback extends Entity {
    private Interview interview;
    private User interviewer;
    private String reason;
    private String feedbackState;

    public InterviewFeedback() {
    }

    public InterviewFeedback(Interview interview, User interviewer, String reason, String feedbackState) {
        this.interview = interview;
        this.interviewer = interviewer;
        this.reason = reason;
        this.feedbackState = feedbackState;
    }

    public Interview getInterview() {
        return interview;
    }

    public final void setInterview(Interview interview) {
        this.interview = interview;
    }

    public final User getInterviewer() {
        return interviewer;
    }

    public final void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }

    public final String getReason() {
        return reason;
    }

    public final void setReason(String reason) {
        this.reason = reason;
    }

    public final String getFeedbackState() {
        return feedbackState;
    }

    public final void setFeedbackState(String feedbackState) {
        this.feedbackState = feedbackState;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewFeedback that = (InterviewFeedback) o;
        return Objects.equals(interview, that.interview) &&
                Objects.equals(interviewer, that.interviewer) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(feedbackState, that.feedbackState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interview, interviewer, reason, feedbackState);
    }

    @Override
    public String toString() {
        return "InterviewFeedback{"
                + "interview=" + interview
                + ", interviewer=" + interviewer
                + ", reason='" + reason + '\''
                + ", feedbackState='" + feedbackState + '\'' + '}';
    }
}
