package dev.java.db.model1;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Candidate extends AbstractEntity {
    private String name;
    private String surname;
    private Date birthday;
    private BigDecimal salaryInDollars;
    private Set<CandidateExperience> experiences = new HashSet<>();
    private Set<Skill> skills = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return super.getId();
    }


    @Basic
    @Column(name = "name", nullable = false, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 255)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Date getBirthday() {
        if (birthday == null) {
            return null;
        }
        return new Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        if (birthday == null) {
            this.birthday = null;
            return;
        }
        this.birthday = new Date(birthday.getTime());
    }

    @Basic
    @Column(name = "salary_in_dollars", nullable = true, precision = 2)
    @SuppressWarnings("checkstyle:MagicNumber")
    public BigDecimal getSalaryInDollars() {
        return salaryInDollars;
    }

    public void setSalaryInDollars(BigDecimal salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    @ManyToMany
    @JoinTable(name = "candidate_competence",
            joinColumns = {@JoinColumn(name = "id_candidate", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill", referencedColumnName = "name")}
    )
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @OneToMany(mappedBy = "candidate")
    public Set<CandidateExperience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<CandidateExperience> experiences) {
        this.experiences = experiences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return Objects.equals(name, candidate.name)
               && Objects.equals(surname, candidate.surname)
               && Objects.equals(birthday, candidate.birthday)
               && Objects.equals(salaryInDollars, candidate.salaryInDollars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday);
    }
}
