package dev.java.db.model;

import java.sql.Date;
import java.util.Objects;

public class Interview extends Entity {
    //private Candidate candidate;
    //private Vacancy vacancy;
    private int id_candidate;
    private String candidate_text;
    private int id_vacancy;
    private String vacancy_text;
    private Date planDate;
    private Date factDate;

    public Interview() {
    }

    public Interview(long id) {
        super(id);
    }

    public Interview(Date planDate, Date factDate, int id_candidate, int id_vacancy,
                     String candidate_text, String vacancy_text) {
        this.id_candidate = id_candidate;
        this.id_vacancy = id_vacancy;
        this.candidate_text = candidate_text;
        this.vacancy_text = vacancy_text;
        this.planDate = planDate;
        this.factDate = factDate;
    }

    public int getCandidateId() {
        return id_candidate;
    }
    public void setCandidateId(int id_candidate) {
        this.id_candidate = id_candidate;
    }

    public int getVacancyId() {
        return id_vacancy;
    }
    public void setVacancyId(int id_vacancy) {
        this.id_vacancy = id_vacancy;
    }

    public String getCandidate_text() {
        return candidate_text;
    }
    public void setCandidate_text(String candidate_text) {
        this.candidate_text = candidate_text;
    }

    public String getVacancy_text() {
        return vacancy_text;
    }
    public void setVacancy_text(String vacancy_text) {
        this.vacancy_text = vacancy_text;
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
        return Objects.equals(id_candidate, interview.id_candidate) &&
                Objects.equals(id_vacancy, interview.id_vacancy) &&
                Objects.equals(planDate, interview.planDate) &&
                Objects.equals(factDate, interview.factDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_candidate, id_vacancy, planDate, factDate);
    }

    @Override
    public String toString() {
        String res =  String.format("Interview ID:%d  id_cand:%d [%s]  id_vac:%d [%s] PlanD:%s  FactD:%s",
          getId(), id_candidate, candidate_text, id_vacancy, vacancy_text, planDate, factDate);
        return res;
    }
}
