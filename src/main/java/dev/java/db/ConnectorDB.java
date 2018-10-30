package dev.java.db;

import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.InterviewDao;
import dev.java.db.daos.SkillDao;
import dev.java.db.daos.UserDao;
import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;


import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        InterviewDao interviewDao = new InterviewDao(connection);
        Interview interview = interviewDao.getEntityById(4);
        System.out.println(interview);


    }
}

