package dev.java.db.DAOs;

import dev.java.db.model.CandidateSkill;
import dev.java.db.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidateSkillDAO extends AbstractDAO<CandidateSkill>{
    public CandidateSkillDAO(Connection connection, Table table) {
        super(connection, table);
    }

    @Override
    public boolean delete(CandidateSkill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM " + table + " WHERE candidate_id='" + entity.getCandidateId() + "' " +
                    "AND skill_id='" + entity.getSkillId() + "'";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean create(CandidateSkill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO " + table +
                    " (candidate_id, skill_id) " +
                    "VALUES ('";
            sql += entity.getCandidateId() + "', '" + entity.getSkillId() + "')";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean update(CandidateSkill entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE " + table + " SET " +
                    "candidate_id='" + entity.getCandidateId() + "', " +
                    "skill_id='" + entity.getSkillId() + "'" +
                    "WHERE id=" + entity.getId();
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    protected void setFields(ResultSet resultSet, CandidateSkill candidateSkill) throws SQLException {
        candidateSkill.setId(resultSet.getLong("id"));
        candidateSkill.setCandidateId(resultSet.getLong("candidate_id"));
        candidateSkill.setSkillId(resultSet.getLong("skill_id"));
    }
}
