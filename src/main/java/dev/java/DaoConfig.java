package dev.java;

import dev.java.db.daos.CVFeedbackDao;
import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.CandidateFeedbackDao;
import dev.java.db.daos.DevFeedbackDao;
import dev.java.db.daos.HRFeedbackDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.daos.RequirementDao;
import dev.java.db.daos.SkillDao;
import dev.java.db.daos.TMFeedbackDao;
import dev.java.db.daos.UserDao;
import dev.java.db.daos.VacancyDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {
    @Autowired
    private SessionFactory mySessionFactory;

    @Bean
    public CandidateDao candidateDao() {
        return new CandidateDao(mySessionFactory);
    }

    @Bean
    public CandidateFeedbackDao candidateFeedbackDao() {
        return new CandidateFeedbackDao(mySessionFactory);
    }

    @Bean
    public CVFeedbackDao cvFeedbackDao() {
        return new CVFeedbackDao(mySessionFactory);
    }

    @Bean
    public DevFeedbackDao devFeedbackDao() {
        return new DevFeedbackDao(mySessionFactory);
    }

    @Bean
    public HRFeedbackDao hrFeedbackDao() {
        return new HRFeedbackDao(mySessionFactory);
    }

    @Bean
    public InterviewDao interviewDao() {
        return new InterviewDao(mySessionFactory);
    }

    @Bean
    public TMFeedbackDao tmFeedbackDao() {
        return new TMFeedbackDao(mySessionFactory);
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(mySessionFactory);
    }

    @Bean
    public VacancyDao vacancyDao() {
        return new VacancyDao(mySessionFactory);
    }

    @Bean
    public SkillDao skillDao() {
        return new SkillDao(mySessionFactory);
    }

    @Bean
    public RequirementDao requirementDao() {
        return new RequirementDao(mySessionFactory);
    }
}
