package dev.java.db;

import dev.java.db.daos.CandidateDao;
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

        String SQL_INSERT = "INSERT INTO candidate " +
                "(name, surname, birthday, salary_in_dollars, candidate_state) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        preparedStatement.setString(1, "Kseniya");
        preparedStatement.setString(2, "Piliak");
        preparedStatement.setDate(3, Date.valueOf("2008-12-12"));
        preparedStatement.setFloat(4, 200.00f);
        preparedStatement.setString(5, null);
        System.out.println(preparedStatement);


    }
}
