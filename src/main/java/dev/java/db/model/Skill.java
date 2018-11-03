package dev.java.db.model;

import java.util.List;
import java.util.Objects;

public class Skill extends Entity {
    private String name;
    private List<Vacancy> correspondingVacancies;
    private List<Candidate> correspondingCandidates;

    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final List<Vacancy> getCorrespondingVacancies() {
        return correspondingVacancies;
    }

    static public boolean isNameSkillValid(String str){
        if("".equals(str))
            return false;
        str=str.toUpperCase();
        for (int i=0;i<str.length();i++)
            if (!(str.charAt(i)>=48&&str.charAt(i)<=57)&&!(str.charAt(i)>=65&&str.charAt(i)<=90)&&str.charAt(i)!='_')
                return false;
        return true;
    }

    public final void setCorrespondingVacancies(List<Vacancy> correspondingVacancies) {
        this.correspondingVacancies = correspondingVacancies;
    }

    public final List<Candidate> getCorrespondingCandidates() {
        return correspondingCandidates;
    }

    public final void setCorrespondingCandidates(List<Candidate> correspondingCandidates) {
        this.correspondingCandidates = correspondingCandidates;
    }

    @Override
    public final boolean equals(Object o) {
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