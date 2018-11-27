package dev.java.controller;

import dev.java.db.model.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ObjectsFactory {
    static public User getUser() {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.ACTIVE);
        user1.setPassword("sjkgksgs");
        user1.setName("Evgeniy");
        user1.setSurname("Zakrevskiy");
        user1.setEmail("zakrevskiy_evgeniy@mail.ru");
        Role role = new Role();
        role.setName("Developer");
        user1.setUserRole(role);
        return user1;
    }

    static public Vacancy getVacancy() {
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setId(1);
        vacancy1.setPosition("C++ Developer");
        vacancy1.setDeveloper(getUser());
        vacancy1.setVacancyState(Vacancy.VacancyState.OPEN);
        vacancy1.getCandidates().add(getCandidate());
        vacancy1.setExperienceYearsRequire(1.5F);
        vacancy1.setSalaryInDollarsFrom(1500F);
        vacancy1.setSalaryInDollarsTo(2000F);
        Requirement requirement = new Requirement();
        requirement.setName("Nothing");
        vacancy1.getRequirements().add(requirement);
        return vacancy1;
    }

    static public Candidate getCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setSurname("Piliak");
        candidate.setName("Kseniya");
        candidate.setBirthday(Date.valueOf("1996-04-06"));
        candidate.setSalaryInDollars(new BigDecimal(200));
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("HELLO");
        candidate.getSkills().add(skill1);
        candidate.getSkills().add(skill2);
        CandidateState candidateState = new CandidateState();
        candidateState.setName("Invited to interview");
        candidate.setCandidateState(candidateState);
        return candidate;
    }

    public static Interview getInterview() {
        Interview interview = new Interview();
        interview.setId(1);
        interview.setCandidate(getCandidate());
        interview.setVacancy(getVacancy());
        interview.setPlanDate(Timestamp.valueOf("2014-01-01 00:00:00"));
        interview.setFactDate(Timestamp.valueOf("2014-01-01 00:00:00"));
        return interview;
    }
}
