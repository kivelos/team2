package dev.java.db.model1;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "candidate_experience", schema = "staffjobs")
public class CandidateExperience extends AbstractEntity {
    private Date dateFrom;
    private Date dateTo;
    private String jobDescription;
    private String jobPosition;
    private String companyName;
    private Candidate candidate;
    //private Set<Responsibility> responsibilities = new HashSet<>();

    @Basic
    @Column(name = "date_from", nullable = false)
    public Date getDateFrom() {
        if (dateFrom == null) {
            return null;
        }
        return new Date(dateFrom.getTime());
    }

    public void setDateFrom(Date dateFrom) {
        if (dateFrom == null) {
            this.dateFrom = null;
            return;
        }
        this.dateFrom = new Date(dateFrom.getTime());
    }

    @Basic
    @Column(name = "date_to", nullable = false)
    public Date getDateTo() {
        if (dateTo == null) {
            return null;
        }
        return new Date(dateTo.getTime());
    }

    public void setDateTo(Date dateTo) {
        if (dateTo == null) {
            this.dateTo = null;
            return;
        }
        this.dateTo = new Date(dateTo.getTime());
    }

    @Basic
    @Column(name = "job_description", nullable = true, length = 4000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Basic
    @Column(name = "job_position", nullable = true, length = 1000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Basic
    @Column(name = "company_name", nullable = true, length = 1000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "id_candidate", referencedColumnName = "id")
    @JsonIgnore
    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    /*@ManyToMany
    @JoinTable(name = "candidate_responsibility",
            joinColumns = {@JoinColumn(name = "id_candidate_experience", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "responsibility", referencedColumnName = "name")}
    )
    public Set<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(Set<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

   */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateExperience that = (CandidateExperience) o;
        return Objects.equals(dateFrom, that.dateFrom)
               && Objects.equals(dateTo, that.dateTo)
               && Objects.equals(jobDescription, that.jobDescription)
               && Objects.equals(jobPosition, that.jobPosition)
               && Objects.equals(companyName, that.companyName)
               && Objects.equals(candidate, that.candidate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo, jobDescription, jobPosition, companyName, candidate);
    }

    @Override
    public String toString() {
        return "CandidateExperience{"
               + "dateFrom=" + dateFrom
               + ", dateTo=" + dateTo
               + ", jobDescription='" + jobDescription + '\''
               + ", jobPosition='" + jobPosition + '\''
               + ", companyName='" + companyName + '\''
               + ", candidate=" + candidate
               + '}';
    }
}
