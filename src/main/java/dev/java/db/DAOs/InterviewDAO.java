package dev.java.db.DAOs;

import dev.java.db.model.Interview;
import dev.java.db.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterviewDAO extends AbstractDAO<Interview> {
  public InterviewDAO(Connection connection, Table table) {
    super(connection, table);
  }

  @Override
  public boolean delete(Interview entity) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      String sql = String.format("DELETE FROM %s WHERE id=%d", table, entity.getId());
      return statement.executeUpdate(sql) != 0;
    }
  }

  @Override
  public boolean create(Interview entity) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      String sql = String.format("INSERT INTO %s (planDate, factDate, VacancyID, CandidateID) VALUES (%s %s %d %d)",
        table, entity.getFactDate(), entity.getPlanDate(), entity.getVacancyID(), entity.getCandidateID());
      return statement.executeUpdate(sql) != 0;
    }
  }

  @Override
  public boolean update(Interview entity) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      String sql = String.format("UPDATE %s SET  planDate=%s, factDate=%s, VacancyID=%d, CandidateID=%d WHERE id=%d",
        table, entity.getFactDate(), entity.getPlanDate(), entity.getVacancyID(), entity.getCandidateID(), entity.getId());
      return statement.executeUpdate(sql) != 0;
    }
  }

  @Override
  protected void setFields(ResultSet resultSet, Interview interview) throws SQLException {
    interview.setId(resultSet.getLong("id"));
    interview.setPlanDate(resultSet.getDate("plan_interview_date"));
    interview.setFactDate(resultSet.getDate("fact_interview_date"));
    interview.setVacancyID(resultSet.getInt("id_of_current_vacancy"));
    interview.setCandidateID(resultSet.getInt("id_of_current_candidate"));
  }
}
