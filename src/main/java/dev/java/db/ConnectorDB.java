package dev.java.db;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        Candidate candidate = new Candidate();
        candidate.setName("Kseniya");
        candidate.setSurname("Piliak");
        candidate.setBirthday(new Date(96, 3, 6));
        candidate.setSalaryInDollars(200);
        CandidateDao candidateDao = new CandidateDao(connection);
        //candidateDao.createEntity(candidate);
        System.out.println(candidateDao.getEntityById(6));


    }
}
