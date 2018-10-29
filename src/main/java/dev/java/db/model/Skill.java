package dev.java.db.model;

import java.util.List;
import java.util.Objects;

public class Skill extends Entity {
    private String name;
    private List<Vacancy> correspondingVacancies;
    private List<Candidate> correspondingCandidates;

    public Skill() {
    }

    public Skill(String name) {
        this.name = name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public List<Vacancy> getCorrespondingVacancies() {
        return correspondingVacancies;
    }

    static public boolean isNameSkillValid(String str){
        if("".equals(str))
            return false;
        return true;
    }

    public void setCorrespondingVacancies(List<Vacancy> correspondingVacancies) {
        this.correspondingVacancies = correspondingVacancies;
    }

    public List<Candidate> getCorrespondingCandidates() {
        return correspondingCandidates;
    }

    public void setCorrespondingCandidates(List<Candidate> correspondingCandidates) {
        this.correspondingCandidates = correspondingCandidates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name) &&
                Objects.equals(correspondingVacancies, skill.correspondingVacancies) &&
                Objects.equals(correspondingCandidates, skill.correspondingCandidates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, correspondingVacancies, correspondingCandidates);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                '}';
    }
}