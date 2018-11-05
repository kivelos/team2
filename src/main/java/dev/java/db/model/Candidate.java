package dev.java.db.model;

import dev.java.db.model.attachments.Attachment;
import dev.java.db.model.contacts.Contact;
import dev.java.db.model.experiences.Experience;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public final class Candidate extends Entity {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public float getSalaryInDollars() {
        return salaryInDollars;
    }

    public void setSalaryInDollars(float salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    public String getCandidateState() {
        return candidateState;
    }

    public void setCandidateState(String candidateState) {
        this.candidateState = candidateState;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Skill> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Skill> competences) {
        this.competences = competences;
    }

    public List<Vacancy> getPassedVacancies() {
        return passedVacancies;
    }

    public void setPassedVacancies(List<Vacancy> passedVacancies) {
        this.passedVacancies = passedVacancies;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) object;
        return Float.compare(candidate.salaryInDollars, salaryInDollars) == 0
                && Objects.equals(name, candidate.name)
                && Objects.equals(surname, candidate.surname)
                && Objects.equals(birthday, candidate.birthday)
                && Objects.equals(candidateState, candidate.candidateState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, salaryInDollars, candidateState);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", birthday=" + birthday
                + ", salaryInDollars=" + salaryInDollars
                + ", candidateState='" + candidateState + '\''
                + '}';
    }
}
