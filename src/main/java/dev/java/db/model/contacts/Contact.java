package dev.java.db.model.contacts;

import java.util.Objects;

public class Contact {
    private ContactType type;
    private String details;

    public Contact() {
    }

    public Contact(ContactType type, String details) {
        this.type = type;
        this.details = details;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return type == contact.type && Objects.equals(details, contact.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, details);
    }
}
