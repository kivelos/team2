package dev.java.db.model;

import java.util.List;
import java.util.Objects;

public class Vacancy extends Entity {
    private String position;
    private float salaryInDollarsFrom;
    private float salaryInDollarsTo;
    private VacancyState vacancyState;
    private float experienceYearsRequire;
    private User developer;
    private List<Interview> interviews;
    private List<Candidate> passedCandidates;
    private List<Skill> skillRequirements;

    public Vacancy() {
    }

    public Vacancy(long id) {
        super(id);
    }

    public Vacancy(String position, float salaryInDollarsFrom, float salaryInDollarsTo,
                   VacancyState vacancyState, float experienceYearsRequire, User developer) {
        this.position = position;
        this.salaryInDollarsFrom = salaryInDollarsFrom;
        this.salaryInDollarsTo = salaryInDollarsTo;
        this.vacancyState = vacancyState;
        this.experienceYearsRequire = experienceYearsRequire;
        this.developer = developer;
    }

    public final String getPosition() {
        return position;
    }

    public final void setPosition(String position) {
        this.position = position;
    }

    public final float getSalaryInDollarsFrom() {
        return salaryInDollarsFrom;
    }

    public final void setSalaryInDollarsFrom(float salaryInDollarsFrom) {
        this.salaryInDollarsFrom = salaryInDollarsFrom;
    }

    public final float getSalaryInDollarsTo() {
        return salaryInDollarsTo;
    }

    public final void setSalaryInDollarsTo(float salaryInDollarsTo) {
        this.salaryInDollarsTo = salaryInDollarsTo;
    }

    public final VacancyState getVacancyState() {
        return vacancyState;
    }

    public final void setVacancyState(VacancyState vacancyState) {
        this.vacancyState = vacancyState;
    }

    public final float getExperienceYearsRequire() {
        return experienceYearsRequire;
    }

    public final void setExperienceYearsRequire(float experienceYearsRequire) {
        this.experienceYearsRequire = experienceYearsRequire;
    }

    public final User getDeveloper() {
        return developer;
    }

    public final void setDeveloper(User developer) {
        this.developer = developer;
    }

    public final List<Interview> getInterviews() {
        return interviews;
    }

    public final void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    public final List<Candidate> getPassedCandidates() {
        return passedCandidates;
    }

    public final void setPassedCandidates(List<Candidate> passedCandidates) {
        this.passedCandidates = passedCandidates;
    }

    public final List<Skill> getSkillRequirements() {
        return skillRequirements;
    }

    public final void setSkillRequirements(List<Skill> skillRequirements) {
        this.skillRequirements = skillRequirements;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Float.compare(vacancy.salaryInDollarsFrom, salaryInDollarsFrom) == 0 &&
                Float.compare(vacancy.salaryInDollarsTo, salaryInDollarsTo) == 0 &&
                Float.compare(vacancy.experienceYearsRequire, experienceYearsRequire) == 0 &&
                Objects.equals(position, vacancy.position) &&
                vacancyState == vacancy.vacancyState &&
                Objects.equals(developer, vacancy.developer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, salaryInDollarsFrom, salaryInDollarsTo, vacancyState, experienceYearsRequire, developer);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "position='" + position + '\'' +
                ", salaryInDollarsFrom=" + salaryInDollarsFrom +
                ", salaryInDollarsTo=" + salaryInDollarsTo +
                ", vacancyState=" + vacancyState +
                ", experienceYearsRequire=" + experienceYearsRequire +
                ", developer=" + developer +
                '}';
    }
}
