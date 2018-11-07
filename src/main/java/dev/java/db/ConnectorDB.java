package dev.java.db;

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
        /*Connection connection = getConnection();
        InterviewDao interviewDao = new InterviewDao(connection);
        Interview interview = interviewDao.getEntityById(4);
        System.out.println(interview);
        */
    }
}

