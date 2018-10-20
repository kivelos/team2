package dev.java.db;

import dev.java.db.daos.SkillDao;
import dev.java.db.model.Skill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        Skill skill = new Skill();
        skill.setName("java");
        SkillDao skillDao = new SkillDao(connection);
        skillDao.createEntity(skill);


    }
}
