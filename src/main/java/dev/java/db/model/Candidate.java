package dev.java.db.model;

public class Candidate extends Entity {
    private String email = "";
    private String photo = "";
    private String name = "";
    private String surname = "";
    private int salaryInDollars = 0;
    private float experienceInYears = 0;
    private String phone = "";
    private String comment = "";
    private Status status = Status.UNDER_CONSIDERATION;

    public Candidate() {

    }

    public Candidate(long id, String email, String photo, String name, String surname, int salaryInDollars, float experienceInYears, String phone, String comment, Status status) {
        super(id);
        this.email = email;
        this.photo = photo;
        this.name = name;
        this.surname = surname;
        this.salaryInDollars = salaryInDollars;
        this.experienceInYears = experienceInYears;
        this.phone = phone;
        this.comment = comment;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public int getSalaryInDollars() {
        return salaryInDollars;
    }

    public void setSalaryInDollars(int salaryInDollars) {
        this.salaryInDollars = salaryInDollars;
    }

    public float getExperienceInYears() {
        return experienceInYears;
    }

    public void setExperienceInYears(float experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salaryInDollars=" + salaryInDollars +
                ", experienceInYears=" + experienceInYears +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }

    public enum  Status {
        REFUSED,
        UNDER_CONSIDERATION,
        SATISFIED,
        INVITED_TO_INTERVIEW;
    }
}
