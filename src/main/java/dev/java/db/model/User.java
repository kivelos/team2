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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER")
public class User extends AbstractEntity {

    private String email;
    private String password;
    private String surname;
    private String name;
    private State state = State.ACTIVE;
    private Role userRole;
    private List<Vacancy> vacancies = new ArrayList<>();
    private List<CandidateFeedback> feedbackList = new ArrayList<>();

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }


    @Basic
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "USER_STATE")
    @Enumerated(EnumType.STRING)
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }


    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    public List<Vacancy> getVacancies() {
        return vacancies;
    }
    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }



    @ManyToOne
    @JoinTable(name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
    )
    public Role getUserRole() {
        return userRole;
    }
    public void setUserRole(Role role) {
        this.userRole = role;
    }

    @OneToMany(mappedBy = "interviewer")
    @JsonIgnore
    public List<CandidateFeedback> getCandidateFeedbacks() {
        return feedbackList;
    }

    public void setCandidateFeedbacks(List<CandidateFeedback> candidateFeedbacks) {
        this.feedbackList = candidateFeedbacks;
    }

    @Override
    public String toString() {
        return "User{"
               + "email='" + email + '\''
               + ", password='" + password + '\''
               + ", surname='" + surname + '\''
               + ", name='" + name + '\''
               + ", state=" + state + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email)
               && Objects.equals(password, user.password)
               && Objects.equals(surname, user.surname)
               && Objects.equals(name, user.name)
               && state == user.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, surname, name, state);
    }

    public enum State {
        BLOCKED,
        ACTIVE
    }
}

