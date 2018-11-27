package dev.java.db.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy extends AbstractEntity {
    private String position;
    private float salaryInDollarsFrom;
    private float salaryInDollarsTo;
    private VacancyState vacancyState;
    private float experienceYearsRequire;
    private User developer;
    private List<Interview> interviews = new ArrayList<>();
    private List<Requirement> requirements = new ArrayList<>();
    private List<Candidate> candidates = new ArrayList<>();

    @ManyToOne
    @SuppressWarnings("checkstyle:ParamentName")
    @JoinColumn(name = "id_developer", referencedColumnName = "id", nullable = false)
    public User getDeveloper() {
        return developer;
    }
    public void setDeveloper(User idDeveloper) {
        developer = idDeveloper;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @Basic
    @Column(name = "position", nullable = false, length = 1000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "salary_in_dollars_from",  precision = 2)
    public float getSalaryInDollarsFrom() {
        return salaryInDollarsFrom;
    }

    public void setSalaryInDollarsFrom(float salaryInDollarsFrom) {
        this.salaryInDollarsFrom = salaryInDollarsFrom;
    }

    @Basic
    @Column(name = "salary_in_dollars_to", precision = 2)
    public float getSalaryInDollarsTo() {
        return salaryInDollarsTo;
    }

    public void setSalaryInDollarsTo(float salaryInDollarsTo) {
        this.salaryInDollarsTo = salaryInDollarsTo;
    }

    @Basic
    @Column(name = "vacancy_state")
    @Enumerated(EnumType.STRING)
    public VacancyState getVacancyState() {
        return vacancyState;
    }

    public void setVacancyState(VacancyState vacancyState) {
        this.vacancyState = vacancyState;
    }

    @Basic
    @Column(name = "experience_years_require", precision = 2)
    public float getExperienceYearsRequire() {
        return experienceYearsRequire;
    }

    public void setExperienceYearsRequire(float experienceYearsRequire) {
        this.experienceYearsRequire = experienceYearsRequire;
    }

    @OneToMany(mappedBy = "vacancy")
    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    @ManyToMany
    @JoinTable(name = "vacancy_requirement",
            joinColumns = {@JoinColumn(name = "id_vacancy", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "requirement", referencedColumnName = "name")}
    )
    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    @ManyToMany
    @JoinTable(name = "vacancy_candidates",
            joinColumns = {@JoinColumn(name = "id_vacancy", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_candidate", referencedColumnName = "id")}
    )
    @JsonIgnore
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy that = (Vacancy) o;
        return Objects.equals(position, that.position)
               && Objects.equals(salaryInDollarsFrom, that.salaryInDollarsFrom)
               && Objects.equals(salaryInDollarsTo, that.salaryInDollarsTo)
               && Objects.equals(vacancyState, that.vacancyState)
               && Objects.equals(experienceYearsRequire, that.experienceYearsRequire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, salaryInDollarsFrom, salaryInDollarsTo, vacancyState, experienceYearsRequire);
    }

    public enum VacancyState {
        OPEN,
        CLOSE
    }
}
