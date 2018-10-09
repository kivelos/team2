package dev.java.db.DAOs;

import dev.java.db.model.Skill;
import dev.java.db.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillDAO extends AbstractDAO<Skill> {
    public SkillDAO(Connection connection, Table table) {
        super(connection, table);
    }

    @Override
    public boolean delete(Skill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM skill WHERE name='" + entity.getName() + "'";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean create(Skill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO " + table +
                    " (name) " +
                    "VALUES ('";
            sql += entity.getName() + "')";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean update(Skill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE " + table + " SET " +
                    "name='" + entity.getName() + "'" +
                    "WHERE id=" + entity.getId();
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    protected void setFields(ResultSet resultSet, Skill skill) throws SQLException {
        skill.setId(resultSet.getLong("id"));
        skill.setName(resultSet.getString("name"));
    }
}
