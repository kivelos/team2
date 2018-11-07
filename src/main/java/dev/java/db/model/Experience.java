package dev.java.db.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Experience implements Serializable {
    private Date dateFrom;
    private Date dateTo;

    public Experience() {
    }

    public Experience(Date dateFrom, Date dateTo) {
        this.dateFrom = new Date(dateFrom.getTime());
        this.dateTo = new Date(dateTo.getTime());
    }

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
        Experience that = (Experience) o;
        return Objects.equals(dateFrom, that.dateFrom) && Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom, dateTo);
    }
}
