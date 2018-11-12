package dev.java.db;

import dev.java.db.model1.Candidate;
import dev.java.db.model1.CandidateExperience;
import dev.java.db.model1.CandidateExperiencePK;
import dev.java.db.model1.Skill;
import dev.java.db.utils.HibernateSessionFactory;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public final class ConnectorDB {
    private ConnectorDB() {

    }

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) throws SQLException {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        Candidate candidate = new Candidate();
        candidate.setName("Sergey");
        candidate.setSurname("Zyazyulkin");
        CandidateExperience candidateExperience = new CandidateExperience();
        candidateExperience.setCandidate(candidate);
        candidateExperience.setDateFrom(new Date(2018, 11, 10));
        candidateExperience.setDateTo(new Date(2019, 11, 11));

        candidate.getExperiences().add(candidateExperience);

        Skill skill = new Skill();
        skill.setName("HELLO");
        candidate.getSkills().add(skill);

        session.save(candidate);
        session.flush();
//        session.save(candidateExperience);
        session.getTransaction().commit();


        HibernateSessionFactory.shutdown();


    }
}

