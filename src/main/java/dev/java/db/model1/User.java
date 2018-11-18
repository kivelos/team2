package dev.java.db.model1;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    private String email;
    private String password;
    private String surname;
    private String name;
    private State state = State.ACTIVE;
    private Role userRole;
    private Set<Vacancy> vacancies = new HashSet<>();

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }


    @Basic
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "email", nullable = false, length = 100, unique = true)
    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }


    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    public Set<Vacancy> getVacancies() {
        return vacancies;
    }
    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }



    @ManyToOne
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    public Role getUserRole() {
        return userRole;
    }
    public void setUserRole(Role role) {
        this.userRole = role;
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

