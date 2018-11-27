package dev.java.db.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
public class Attachment {
    private String filePath;
    private AttachmentType attachmentType;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Enumerated(EnumType.STRING)
    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment that = (Attachment) o;
        return Objects.equals(filePath, that.filePath)
               && attachmentType == that.attachmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, attachmentType);
    }

    @Override
    public String toString() {
        return "Attachment{"
               + "filePath='" + filePath + '\''
               + ", attachmentType=" + attachmentType
               + '}';
    }

    public enum AttachmentType {
        CV,
        COVER_LETTER,
        PHOTO
    }
}
