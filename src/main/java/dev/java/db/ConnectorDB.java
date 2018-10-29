package dev.java.db;

import dev.java.db.daos.CandidateDao;
import dev.java.db.daos.SkillDao;
import dev.java.db.model.Candidate;


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
        //CandidateDao candidateDao = new CandidateDao(connection);
        //List<Candidate> list = candidateDao.getFilteredEntitiesPage();
        //list = candidateDao.getSortedEntitiesPage(1, "surname", 10);
        //System.out.println(list);

        SkillDao skillDao = new SkillDao(connection);
        System.out.println(skillDao.getSortedEntitiesPage(1, "name", true, 10));

    }
}

