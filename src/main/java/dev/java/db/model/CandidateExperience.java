package dev.java.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CANDIDATE_EXPERIENCE", schema = "team6")
public class CandidateExperience extends AbstractEntity {
    private Date dateFrom;
    private Date dateTo;
    private String jobDescription;
    private String jobPosition;
    private String companyName;
    private Candidate candidate;
    private List<Responsibility> responsibilities = new ArrayList<>();

    @Basic
    @Column(name = "DATE_FROM", nullable = false)
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
    @Column(name = "DATE_TO", nullable = false)
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
    @Column(name = "JOB_DESCRIPTION", nullable = true, length = 4000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Basic
    @Column(name = "JOB_POSITION", nullable = true, length = 1000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Basic
    @Column(name = "COMPANY_NAME", nullable = true, length = 1000)
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID", referencedColumnName = "ID")
    @JsonIgnore
    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    @ManyToMany
    @JoinTable(name = "CANDIDATE_RESPONSIBILITY",
            joinColumns = {@JoinColumn(name = "CANDIDATE_EXPERIENCE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "RESPONSIBILITY", referencedColumnName = "NAME")}
    )
    public List<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

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
