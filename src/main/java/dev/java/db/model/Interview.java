package dev.java.db.model;

import java.util.Date;

public class Interview extends Entity {
  Date planDate;
  Date factDate;
  long VacancyID;
  long CandidateID;

  Interview() {

  }

  Interview(Date planDate, Date factDate, long VacancyID, long CanditateID) {
    this.planDate = planDate;
    this.factDate = factDate;
    this.VacancyID = VacancyID;
    this.CandidateID = CanditateID;
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

  public long getVacancyID() {
    return this.VacancyID;
  }

  public long getCandidateID() {
    return this.CandidateID;
  }

  public void setVacancyID(long VacancyID) {
    this.VacancyID = VacancyID;
  }

  public void setCandidateID(long CandidateID) {
    this.CandidateID = CandidateID;
  }

  @Override
  public String toString() {
    return String.format("Interview()");
  }
}
