package dev.java.db.DAOs;

import dev.java.db.model.Candidate;
import dev.java.db.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidateDAO extends AbstractDAO<Candidate> {
    public CandidateDAO(Connection connection, Table table) {
        super(connection, table);
    }

    @Override
    public boolean delete(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM " + table + " WHERE email='" + entity.getEmail() + "'";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean create(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO " + table +
                    " (email, photo, name, surname, salary_in_dollars, " +
                    "experience_in_years, phone, comment, status) " +
                    "VALUES ('";
            sql += entity.getEmail() + "', '" +
                    entity.getPhoto() + "', '" +
                    entity.getName() + "', '" +
                    entity.getSurname() + "', '" +
                    entity.getSalaryInDollars() + "', '" +
                    entity.getExperienceInYears() + "', '" +
                    entity.getPhone() + "', '" +
                    entity.getComment() + "', '" +
                    entity.getStatus() + "')";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean update(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE " + table +" SET " +
                    "email='" + entity.getEmail() + "', " +
                    "photo='" + entity.getPhoto() + "', " +
                    "name='" + entity.getName() + "', " +
                    "surname='" + entity.getSurname() + "', " +
                    "salary_in_dollars='" + entity.getSalaryInDollars() + "', " +
                    "experience_in_years='" + entity.getExperienceInYears() + "', " +
                    "phone='" + entity.getPhone() + "', " +
                    "comment='" + entity.getComment() + "', " +
                    "status='" + entity.getStatus() + "'" +
                    "WHERE id=" + entity.getId();
            return statement.executeUpdate(sql) != 0;
        }
    }


    @Override
    protected void setFields(ResultSet resultSet, Candidate candidate) throws SQLException {
        candidate.setId(resultSet.getLong("id"));
        candidate.setEmail(resultSet.getString("email"));
        candidate.setPhoto(resultSet.getString("photo"));
        candidate.setName(resultSet.getString("name"));
        candidate.setSurname(resultSet.getString("surname"));
        candidate.setSalaryInDollars(resultSet.getInt("salary_in_dollars"));
        candidate.setExperienceInYears(resultSet.getFloat("experience_in_years"));
        candidate.setPhone(resultSet.getString("phone"));
        candidate.setComment(resultSet.getString("comment"));
        candidate.setStatus(Candidate.Status.valueOf(resultSet.getString("status")));
    }
}
