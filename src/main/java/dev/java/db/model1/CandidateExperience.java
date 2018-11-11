/*package dev.java.db.model1;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "candidate_experience", schema = "staffjobs", catalog = "")
public class CandidateExperience implements Serializable {

    @EmbeddedId
    private CandidateExperiencePK id;

    public CandidateExperiencePK getId() {
        return id;
    }

    public void setId(CandidateExperiencePK id) {
        this.id = id;
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Embeddable
    public class CandidateExperiencePK implements Serializable {
        //private Candidate candidate;
        private Date dateFrom;
        private Date dateTo;

        @ManyToOne
        @JoinColumn(name = "id_candidate")
        public Candidate getCandidate() {
            return candidate;
        }

        public void setCandidate(Candidate candidate) {
            this.candidate = candidate;
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CandidateExperiencePK that = (CandidateExperiencePK) o;
            return Objects.equals(candidate, that.candidate)
                   && Objects.equals(dateFrom, that.dateFrom)
                   && Objects.equals(dateTo, that.dateTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(candidate, dateFrom, dateTo);
        }
    }
}*/
