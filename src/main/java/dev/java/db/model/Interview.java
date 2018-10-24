package dev.java.db.model;

import java.sql.Date;
import java.util.Objects;

public class Interview extends Entity {
    private Candidate candidate;
    private Vacancy vacancy;
    private Date planDate;
    private Date factDate;


    public Interview() {
    }

    public Interview(long id) {
        super(id);
    }

    public Interview(Candidate candidate, Vacancy vacancy, Date planDate, Date factDate) {
        this.candidate = candidate;
        this.vacancy = vacancy;
        this.planDate = planDate;
        this.factDate = factDate;
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

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getFactDate() {
        return factDate;
    }

    public void setFactDate(Date factDate) {
        this.factDate = factDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview interview = (Interview) o;
        return Objects.equals(candidate, interview.candidate) &&
                Objects.equals(vacancy, interview.vacancy) &&
                Objects.equals(planDate, interview.planDate) &&
                Objects.equals(factDate, interview.factDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, vacancy, planDate, factDate);
    }

    @Override
    public String toString() {
        return "Interview{" +
                "candidate=" + candidate +
                ", vacancy=" + vacancy +
                ", planDate=" + planDate +
                ", factDate=" + factDate +
                '}';
    }
}
