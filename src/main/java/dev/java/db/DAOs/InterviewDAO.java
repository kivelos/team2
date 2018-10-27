package dev.java.db.daos;

import dev.java.db.model.Candidate;
import dev.java.db.model.Interview;
import dev.java.db.model.Vacancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterviewDao extends AbstractDao<Interview> {
    public InterviewDao(Connection connection) {
        super(connection);
        //
        SQL_SELECT_SORTED_PAGE =
        // "SELECT * FROM interview ORDER BY %s %s LIMIT ?, ?"
          "select interview.*, candidate.*, vacancy.* from interview "+
          "join candidate on (interview.id_candidate=candidate.id) "+
          "join vacancy   on (interview.id_vacancy=vacancy.id) " +
          "ORDER BY %s %s LIMIT ?, ?";
        //
        SQL_INSERT = "INSERT INTO interview " +
                "(id_candidate, id_vacancy, plan_date, fact_date) " +
                "VALUES (?, ?, ?, ?)";
        //
        SQL_UPDATE = "UPDATE interview " +
                "SET id_candidate=?, id_vacancy=?, plan_date=?, fact_date=? " +
                "WHERE id=?";
        //
        SQL_SELECT_FILTERED_ENTITIES = "SELECT * FROM interview " +
                "WHERE (id_candidate=? AND id_vacancy=? AND plan_date=? AND fact_date=?)";
    }

    @Override
    protected Interview setEntityFields(ResultSet entityTableRow) throws SQLException {
        Interview interview = new Interview();
        interview.setId(entityTableRow.getLong("id"));
        interview.setCandidateId(entityTableRow.getInt(("id_candidate")));
        interview.setVacancyId(entityTableRow.getInt(("id_vacancy")));
        interview.setCandidate_text(entityTableRow.getString("candidate.name"));
        interview.setVacancy_text(entityTableRow.getString("vacancy.position"));
        interview.setPlanDate(entityTableRow.getDate("plan_date"));
        interview.setFactDate(entityTableRow.getDate("fact_date"));
        return interview;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Interview interview)
            throws SQLException {
        prepareStatement.setLong(1, interview.getCandidateId());
        prepareStatement.setLong(2, interview.getVacancyId());
        prepareStatement.setDate(3, interview.getPlanDate());
        prepareStatement.setDate(4, interview.getFactDate());

    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Interview entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }
}
