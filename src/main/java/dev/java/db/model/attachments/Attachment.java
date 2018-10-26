package dev.java.db.model.attachments;

import java.util.Objects;

public class Attachment {
    private String filePath;
    private AttachmentType type;

    public Attachment() {
    }

    public Attachment(String filePath, AttachmentType type) {
        this.filePath = filePath;
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(filePath, that.filePath) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, type);
    }
}


