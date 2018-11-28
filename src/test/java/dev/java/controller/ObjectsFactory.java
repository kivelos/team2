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

    static public User getAnotherUser() {
        User user1 = new User();
        user1.setId(1);
        user1.setState(User.State.BLOCKED);
        user1.setPassword("dkfsssgerr");
        user1.setName("Olga");
        user1.setSurname("Kivel");
        user1.setEmail("o_kivel@gmail.ru");
        Role role = new Role();
        role.setName("Manager");
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

    static public Vacancy getAnotherVacancy() {
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setId(1);
        vacancy1.setPosition("Java Developer");
        vacancy1.setDeveloper(getAnotherUser());
        vacancy1.setVacancyState(Vacancy.VacancyState.CLOSE);
        vacancy1.getCandidates().add(getCandidate());
        vacancy1.setExperienceYearsRequire(1.0F);
        vacancy1.setSalaryInDollarsFrom(300F);
        vacancy1.setSalaryInDollarsTo(4000F);
        Requirement requirement = new Requirement();
        requirement.setName("What?");
        vacancy1.getRequirements().add(requirement);
        return vacancy1;
    }

    static public FeedbackState getFeedbackState(){
        FeedbackState feedbackState = new FeedbackState();
        feedbackState.setName("Accepted");
        return  feedbackState;
    }

    static public FeedbackState getAnotherFeedbackState(){
        FeedbackState feedbackState = new FeedbackState();
        feedbackState.setName("Denied");
        return  feedbackState;
    }

    static public CVFeedback getCVFeedback(){
        CVFeedback cvFeedback = new CVFeedback();
        cvFeedback.setId(1);
        cvFeedback.setCandidate(getCandidate());
        cvFeedback.setFeedbackState(getFeedbackState());
        cvFeedback.setFeedbackText("Very hard-working!");
        cvFeedback.setInterviewer(getUser());
        return cvFeedback;
    }

    static public CVFeedback getAnotherCVFeedback(){
        CVFeedback cvFeedback = new CVFeedback();
        cvFeedback.setId(2);
        cvFeedback.setCandidate(getAnotherCandidate());
        cvFeedback.setFeedbackState(getAnotherFeedbackState());
        cvFeedback.setFeedbackText("Very nice!");
        cvFeedback.setInterviewer(getAnotherUser());
        return cvFeedback;
    }

    static public HRFeedback getHRFeedback(){
        HRFeedback hrFeedback = new HRFeedback();
        hrFeedback.setId(1);
        hrFeedback.setCandidate(getCandidate());
        hrFeedback.setFeedbackState(getFeedbackState());
        hrFeedback.setFeedbackText("Very hard-working!");
        hrFeedback.setInterviewer(getUser());
        hrFeedback.setInterview(getInterview());
        return hrFeedback;
    }

    static public HRFeedback getAnotherHRFeedback(){
        HRFeedback hrFeedback = new HRFeedback();
        hrFeedback.setId(2);
        hrFeedback.setCandidate(getAnotherCandidate());
        hrFeedback.setFeedbackState(getAnotherFeedbackState());
        hrFeedback.setFeedbackText("Very nice!");
        hrFeedback.setInterviewer(getAnotherUser());
        hrFeedback.setInterview(getAnotherInterview());
        return hrFeedback;
    }

    static public DevFeedback getDevFeedback(){
        DevFeedback devFeedback = new DevFeedback();
        devFeedback.setId(1);
        devFeedback.setCandidate(getCandidate());
        devFeedback.setFeedbackState(getFeedbackState());
        devFeedback.setFeedbackText("Very hard-working!");
        devFeedback.setInterviewer(getUser());
        devFeedback.setInterview(getInterview());
        FeedbackDetails feedbackDetails = new FeedbackDetails();
        Requirement requirement = new Requirement();
        requirement.setName("Java Spring!");
        feedbackDetails.setRequirement(requirement);
        devFeedback.getFeedbackDetails().add(feedbackDetails);
        return devFeedback;
    }

    static public DevFeedback getAnotherDevFeedback(){
        DevFeedback devFeedback = new DevFeedback();
        devFeedback.setId(2);
        devFeedback.setCandidate(getAnotherCandidate());
        devFeedback.setFeedbackState(getAnotherFeedbackState());
        devFeedback.setFeedbackText("Very nice!");
        devFeedback.setInterviewer(getAnotherUser());
        devFeedback.setInterview(getAnotherInterview());
        FeedbackDetails feedbackDetails = new FeedbackDetails();
        Requirement requirement = new Requirement();
        requirement.setName("MySql");
        feedbackDetails.setRequirement(requirement);
        devFeedback.getFeedbackDetails().add(feedbackDetails);
        return devFeedback;
    }

    static public TMFeedback getTMFeedback(){
        TMFeedback tmFeedback = new TMFeedback();
        tmFeedback.setId(1);
        tmFeedback.setCandidate(getCandidate());
        tmFeedback.setFeedbackState(getFeedbackState());
        tmFeedback.setFeedbackText("Very hard-working!");
        tmFeedback.setInterviewer(getUser());
        FeedbackDetails feedbackDetails = new FeedbackDetails();
        Requirement requirement = new Requirement();
        requirement.setName("Java Spring!");
        feedbackDetails.setRequirement(requirement);
        tmFeedback.getFeedbackDetails().add(feedbackDetails);
        return tmFeedback;
    }

    static public TMFeedback getAnotherTMFeedback(){
        TMFeedback tmFeedback = new TMFeedback();
        tmFeedback.setId(2);
        tmFeedback.setCandidate(getAnotherCandidate());
        tmFeedback.setFeedbackState(getAnotherFeedbackState());
        tmFeedback.setFeedbackText("Very nice!");
        tmFeedback.setInterviewer(getAnotherUser());
        FeedbackDetails feedbackDetails = new FeedbackDetails();
        Requirement requirement = new Requirement();
        requirement.setName("MySql");
        feedbackDetails.setRequirement(requirement);
        tmFeedback.getFeedbackDetails().add(feedbackDetails);
        return tmFeedback;
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

    static public Candidate getAnotherCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setName("Pavel");
        candidate.setSurname("Grudinskiy");
        candidate.setBirthday(Date.valueOf("1998-01-01"));
        candidate.setSalaryInDollars(new BigDecimal(100));
        Skill skill1 = new Skill("C++");
        Skill skill2 = new Skill("Java applet");
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

    public static Interview getAnotherInterview() {
        Interview interview = new Interview();
        interview.setId(2);
        interview.setCandidate(getAnotherCandidate());
        interview.setVacancy(getAnotherVacancy());
        interview.setPlanDate(Timestamp.valueOf("2014-01-01 00:10:00"));
        interview.setFactDate(Timestamp.valueOf("2014-01-01 00:10:10"));
        return interview;
    }
}
