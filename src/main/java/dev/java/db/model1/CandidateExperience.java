package dev.java.db.model1;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class CandidateExperience implements Serializable {
    private Date dateFrom;
    private Date dateTo;

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
        CandidateExperience that = (CandidateExperience) o;
        return Objects.equals(dateFrom, that.dateFrom)
               && Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "CandidateExperience{"
               + "dateFrom=" + dateFrom
               + ", dateTo=" + dateTo
               + '}';
    }
}
