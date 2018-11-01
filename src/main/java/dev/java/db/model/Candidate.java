package dev.java.db.model;

import dev.java.db.model.attachments.Attachment;
import dev.java.db.model.contacts.Contact;
import dev.java.db.model.experiences.Experience;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Candidate extends Entity {
    private String name;
    private String surname;
    private Date birthday;
    private float salaryInDollars;
    private String candidateState;
    private List<Attachment> attachments;
    private List<Experience> experiences;
    private List<Contact> contacts;
    private List<Skill> competences;
    private List<Vacancy> passedVacancies;
    private List<Interview> interviews;

    public Candidate() {
    }

    public Candidate(long id) {
        super(id);
    }

    public Candidate(String name, String surname, Date birthday, float salaryInDollars) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.salaryInDollars = salaryInDollars;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getSurname() {
        return surname;
    }

    public final void setSurname(String surname) {
        this.surname = surname;
    }

    public final Date getBirthday() {
        return birthday;
    }

    public final void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public final float getSalaryInDollars() {
        return salaryInDollars;
    }

    public final void setSalaryInDollars(float salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    public final String getCandidateState() {
        return candidateState;
    }

    public final void setCandidateState(String candidateState) {
        this.candidateState = candidateState;
    }

    public final List<Attachment> getAttachments() {
        return attachments;
    }

    public final void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public final List<Experience> getExperiences() {
        return experiences;
    }

    public final void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public final List<Contact> getContacts() {
        return contacts;
    }

    public final void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public final List<Skill> getCompetences() {
        return competences;
    }

    public final void setCompetences(List<Skill> competences) {
        this.competences = competences;
    }

    public final List<Vacancy> getPassedVacancies() {
        return passedVacancies;
    }

    public final void setPassedVacancies(List<Vacancy> passedVacancies) {
        this.passedVacancies = passedVacancies;
    }

    public final List<Interview> getInterviews() {
        return interviews;
    }

    public final void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Float.compare(candidate.salaryInDollars, salaryInDollars) == 0 &&
                Objects.equals(name, candidate.name) &&
                Objects.equals(surname, candidate.surname) &&
                Objects.equals(birthday, candidate.birthday) &&
                Objects.equals(candidateState, candidate.candidateState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, salaryInDollars, candidateState);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", birthday=" + birthday
                + ", salaryInDollars=" + salaryInDollars
                + ", candidateState='" + candidateState + '\'' + '}';
    }
}
