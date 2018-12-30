package dev.java.db.model;

import javax.persistence.Embeddable;
import java.util.Objects;


@Embeddable
public class ContactDetails {
    private String contactType;
    private String contactDetails;

    //@Enumerated(EnumType.STRING)
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDetails that = (ContactDetails) o;
        return contactType == that.contactType
               && Objects.equals(contactDetails, that.contactDetails);
    }

    @Override
    public String toString() {
        return "ContactDetails{"
               + "contactType=" + contactType
               + ", contactDetails='" + contactDetails + '\''
               + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactType, contactDetails);
    }

    public enum ContactType {
        EMAIL,
        PHONE,
        SKYPE
    }
}
