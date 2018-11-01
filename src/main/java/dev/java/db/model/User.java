package dev.java.db.model;

import java.util.Objects;

public class User extends Entity {
    private String email;
    private String password;
    private String surname;
    private String name;
    private State state=State.ACTIVE;

    public User(){ }

    public User(long id) {
        super(id);
    }

    public User(String email, String password, String name, String surname,State state){
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.password = password;
        this.state = state;
    }

    public final String getEmail() {
        return email;
    }
    public final String getPassword() {
        return password;
    }
    public final String getSurname() {
        return surname;
    }
    public final String getName() {
        return name;
    }
    public final State getState() {
        return state;
    }

    public final void setEmail(String email) {
        this.email = email;
    }
    public final void setPassword(String password) {
        this.password = password;
    }
    public final void setName(String name) {
        this.name = name;
    }
    public final void setState(State state) {
        this.state = state;
    }
    public final void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(name, user.name) &&
                state == user.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, surname, name, state);
    }

    @Override
    public String toString() {
        return "User{" +
                "E-mail: "+email+"\n" +
                "Password: " + password +
                "Surname: " + surname+"\n" +
                "Name: " + name+"\n" +
                "State: " + state + "}";
    }

    static public boolean isPasswordValid(String str){
        if(str.length()>=6&&str.length()<=24)
            return true;
        return false;
    }
    public enum State{BLOCKED,ACTIVE}
}
