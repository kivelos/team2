package dev.java.db.model1;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }


    @Basic
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "surname")
    private String surname;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private State state = State.ACTIVE;



    private Set<Vacancy> vacancies = new HashSet<>();
    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    public Set<Vacancy> getVacancies() {
        return vacancies;
    }
    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

