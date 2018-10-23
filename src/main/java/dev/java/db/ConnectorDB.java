package dev.java.db;

import dev.java.db.daos.CandidateDao;
import dev.java.db.model.Candidate;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectorDB {
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        CandidateDao candidateDao = new CandidateDao(connection);
        List<Candidate> list = candidateDao.getSortedFilteredEntitiesPage(1, "surname", false,
                "name", "Kot", 2);
        //list = candidateDao.getSortedEntitiesPage(1, "surname", 10);
        System.out.println(list);


    }
}
