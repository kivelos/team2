package dev.java.db.model;

public class CandidateSkill extends Entity {
    private long candidateId;
    private long skillId;

    public CandidateSkill() {
    }

    public CandidateSkill(long id, long candidateId, long skillId) {
        super(id);
        this.candidateId = candidateId;
        this.skillId = skillId;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CandidateSkill{" +
                "candidateId=" + candidateId +
                ", skillId=" + skillId +
                '}';
    }

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }
}
