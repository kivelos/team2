package dev.java.db.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Interview extends Entity {
    private Candidate candidate;
    private Vacancy vacancy;
    private Timestamp planDate;
    private Timestamp factDate;


    public Interview() { }

    public Interview(long id) {
        super(id);
    }

    public Interview(Candidate candidate, Vacancy vacancy, Timestamp planDate, Timestamp factDate) {
        this.candidate = candidate;
        this.vacancy = vacancy;
        this.planDate = new Timestamp(planDate.getTime());
        this.factDate = new Timestamp(factDate.getTime());
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Timestamp getPlanDate() {
        if (planDate == null) {
            return null;
        }
        return new Timestamp(planDate.getTime());
    }

    public void setPlanDate(Timestamp planDate) {
        if (planDate == null) {
            this.planDate = null;
            return;
        }
        this.planDate = new Timestamp(planDate.getTime());
    }

    public Timestamp getFactDate() {
        if (factDate == null) {
            return null;
        }
        return new Timestamp(factDate.getTime());
    }

    public void setFactDate(Timestamp factDate) {
        if (factDate == null) {
            this.factDate = null;
            return;
        }
        this.factDate = new Timestamp(factDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Interview interview = (Interview) o;
        return Objects.equals(candidate, interview.candidate)
                && Objects.equals(vacancy, interview.vacancy)
                && Objects.equals(planDate, interview.planDate)
                && Objects.equals(factDate, interview.factDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, vacancy, planDate, factDate);
    }

    @Override
    public String toString() {
        return "Interview{"
                + "candidate=" + candidate
                + ", vacancy=" + vacancy
                + ", planDate=" + planDate
                + ", factDate=" + factDate
                + '}';
    }
}
