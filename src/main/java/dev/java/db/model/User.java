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
        this.email=email;
        this.surname=surname;
        this.name=name;
        this.password=password;
        this.state=state;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public State getState() {
        return state;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
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
                "Password: "+password +
                "Surname: "+surname+"\n" +
                "Name: "+name+"\n" +
                "State: "+state+"}";
    }

    static public boolean isPasswordValid(String str){
        if(str.length()>=6&&str.length()<=24)
            return true;
        return false;
    }
    public enum State{BLOCKED,ACTIVE}
}