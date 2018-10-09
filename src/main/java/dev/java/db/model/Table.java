package dev.java.db.model;

public enum Table {
    CANDIDATE("candidate"),
    SKILL("skill"),
    CANDIDATE_SKILL("candidate_skill");

    private String table;

    Table(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    @Override
    public String toString() {
        return table;
    }
}
