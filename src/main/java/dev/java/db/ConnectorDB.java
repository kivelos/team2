package dev.java.db;

import dev.java.db.utils.HibernateSessionFactory;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
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

    public static void main(String[] args) throws SQLException, ParseException {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        /*CandidateDao candidateDao = new CandidateDao(session);
        Candidate candidate = new Candidate();
        candidate.setName("Noda");
        candidate.setSurname("Megumi");
        String strDate = "2008-12-11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse(strDate);
        java.sql.Date sqlDate = new Date(date.getTime());
        candidate.setBirthday(sqlDate);
        List<Candidate> result = candidateDao.getCandidatesByPersonalData(candidate);
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setContactDetails("+375444859574");
        result = candidateDao.getCandidatesByContact(contactDetails);*/


        /*Vacancy vacancy = new Vacancy();
        vacancy.setPosition("Example position");
        vacancy.setVacancyState(Vacancy.VacancyState.OPEN);
        Requirement requirement = new Requirement();
        requirement.setName("English Intermediate");
        vacancy.getRequirements().add(requirement);
        Candidate candidate = new Candidate();
        candidate.setId(79);
        vacancy.getCandidates().add(candidate);
        User user = new User();
        user.setId(1);
        vacancy.setDeveloper(user);*/

        /*Candidate candidate = new Candidate();
        candidate.setName("Sergey");
        candidate.setSurname("Zyazyulkin");
        CandidateExperience candidateExperience = new CandidateExperience();
        candidateExperience.setDateFrom(Date.valueOf("2018-11-11"));
        candidateExperience.setDateTo(Date.valueOf("2019-11-11"));
        candidateExperience.setCompanyName("Epam");
        candidateExperience.setJobPosition("Java Developer");
        candidate.getExperiences().add(candidateExperience);
        candidateExperience.setCandidate(candidate);

        Skill skill = new Skill();
        skill.setName("Java");
        candidate.getSkills().add(skill);

        CandidateState candidateState = new CandidateState();
        candidateState.setName("Invited to interview");
        candidate.setCandidateState(candidateState);
        Attachment attachment = new Attachment();
        attachment.setFilePath("C:/hello.txt");
        attachment.setAttachmentType(Attachment.AttachmentType.COVER_LETTER);
        candidate.getAttachments().add(attachment);


        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setContactType(ContactDetails.ContactType.PHONE);
        contactDetails.setContactDetails("+375444859574");
        candidate.getContactDetails().add(contactDetails);

        Responsibility responsibility = new Responsibility();
        responsibility.setName("Admin DB");
        candidateExperience.getResponsibilities().add(responsibility);

        session.save(candidate);*/
        session.getTransaction().commit();
        HibernateSessionFactory.shutdown();

    }
}

