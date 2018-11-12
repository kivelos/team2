package dev.java.db;

import dev.java.db.model1.Candidate;
import dev.java.db.model1.ContactDetails;
import dev.java.db.utils.HibernateSessionFactory;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
        /*CandidateExperience candidateExperience = new CandidateExperience();
        candidateExperience.setDateFrom(Date.valueOf("2018-11-11"));
        candidateExperience.setDateTo(Date.valueOf("2019-11-11"));
        candidate.getExperiences().add(candidateExperience);

        Skill skill = new Skill();
        skill.setName("HELLO");
        candidate.getSkills().add(skill);
        */
        /*CandidateState candidateState = new CandidateState();
        candidateState.setName("Invited to interview");
        candidate.setCandidateState(candidateState);*/
        /*Attachment attachment = new Attachment();
        attachment.setFilePath("C:/hello.txt");
        attachment.setAttachmentType(Attachment.AttachmentType.COVER_LETTER);
        candidate.getAttachments().add(attachment);*/
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setContactType(ContactDetails.ContactType.PHONE);
        contactDetails.setContactDetails("+375444859574");
        candidate.getContactDetails().add(contactDetails);
        session.save(candidate);
        session.getTransaction().commit();

        HibernateSessionFactory.shutdown();

    }
}

