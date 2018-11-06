package dev.java.db.model;

import java.util.Objects;

public class User extends Entity {
    public static final int MIN_PASSWORD_SIZE = 6;
    public static final int MAX_SIZE_PASSWORD = 24;

    private String email;
    private String password;
    private String surname;
    private String name;
    private State state = State.ACTIVE;

    public User() {
    }

    public User(long id) {
        super(id);
    }

    public User(String email, String password, String name, String surname, State state) {
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.state = state;
    }

    public static boolean isPasswordValid(String str) {
        return str.length() >= MIN_PASSWORD_SIZE && str.length() <= MAX_SIZE_PASSWORD;
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

    @Override
    public String toString() {
        return "User{E-mail: " + email
                + "\nPassword: " + password
                + "Surname: " + surname
                + "\nName: " + name
                + "\nState: " + state
                + "}";
    }

    public enum State {
        BLOCKED,
        ACTIVE
    }
}
