package dev.java.db.model1;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "interview", schema = "staffjobs")
public class Interview extends AbstractEntity {
    private Candidate candidate;
    private Vacancy vacancy;
    private Timestamp planDate;
    private Timestamp factDate;


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "id_candidate", referencedColumnName = "id", nullable = false)
    public Candidate getCandidate() {
        return candidate;
    }
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    @ManyToOne
    @JoinColumn(name = "id_vacancy", referencedColumnName = "id", nullable = false)
    public Vacancy getVacancy() {
        return vacancy;
    }
    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Basic
    @Column(name = "plan_date")
    public Timestamp getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Timestamp planDate) {
        this.planDate = planDate;
    }

    @Basic
    @Column(name = "fact_date")
    public Timestamp getFactDate() {
        return factDate;
    }

    public void setFactDate(Timestamp factDate) {
        this.factDate = factDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Interview interview = (Interview) o;
        return Objects.equals(candidate, interview.candidate)
                && Objects.equals(vacancy, interview.vacancy)
                && Objects.equals(planDate, interview.planDate)
                && Objects.equals(factDate, interview.factDate);
    }

    @Override
    public int hashCode()  {
        return Objects.hash(candidate, vacancy, planDate, factDate);
    }

    public String toString() {
        return "Interview{"
                + "candidate='" + candidate + '\''
                + ", vacancy='" + vacancy + '\''
                + ", planDate=" + planDate + '\''
                + ", factDate=" + factDate + '\''
                + '}';
    }
}
