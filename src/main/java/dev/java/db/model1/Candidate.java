package dev.java.db.model1;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Candidate extends AbstractEntity {
    private String name;
    private String surname;
    private Date birthday;
    private BigDecimal salaryInDollars;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.birthday = birthday;
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

    @Override
    @SuppressWarnings("checkstyle:AvoidInlineConditionals")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Candidate candidate = (Candidate) o;

        if (id != candidate.id) {
            return false;
        }
        if (name != null ? !name.equals(candidate.name) : candidate.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(candidate.surname) : candidate.surname != null) {
            return false;
        }
        if (birthday != null ? !birthday.equals(candidate.birthday) : candidate.birthday != null) {
            return false;
        }
        if (salaryInDollars != null ? !salaryInDollars.equals(
                candidate.salaryInDollars) : candidate.salaryInDollars != null) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:AvoidInlineConditionals"})
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (salaryInDollars != null ? salaryInDollars.hashCode() : 0);
        return result;
    }
}
