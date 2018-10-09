package dev.java.db.DAOs;

import dev.java.db.model.Candidate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO extends AbstractDAO<Candidate> {
    public CandidateDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Candidate> findAll() throws SQLException {
        List<Candidate> candidates = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM candidate");
            while (resultSet.next()) {
                Candidate candidate = new Candidate();
                setFields(resultSet, candidate);
                candidates.add(candidate);
            }
            return candidates;
        }
    }

    @Override
    public Candidate findEntityById(int id) throws SQLException {
        return findEntityByFiled("id", String.valueOf(id));
    }

    @Override
    public Candidate findEntityByFiled(String field, String value) throws SQLException {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM candidate WHERE " + field + "='" + value + "'");
            resultSet.next();
            Candidate candidate = new Candidate();
            setFields(resultSet, candidate);
            return candidate;
        }

    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM candidate WHERE id=" + id;
            return statement.executeUpdate(sql) != 0;
        }

    }

    @Override
    public boolean delete(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM candidate WHERE email='" + entity.getEmail() + "'";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public Candidate create(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO candidate " +
                    "(email, photo, name, surname, salary_in_dollars, " +
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
            if (statement.executeUpdate(sql) != 0) {
                return findEntityByFiled("email", entity.getEmail());
            }
            return null;
        }
    }

    @Override
    public boolean update(Candidate entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE candidate SET " +
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


    private void setFields(ResultSet resultSet, Candidate candidate) throws SQLException {
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
