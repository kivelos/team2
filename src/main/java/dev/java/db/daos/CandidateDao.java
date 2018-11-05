package dev.java.db.daos;

import dev.java.db.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class CandidateDao extends AbstractDao<Candidate> {

    //language=SQL
    private String sqlSelectSkills = "SELECT skill.name FROM skill "
            + "JOIN vacancy_requirement v on skill.name = v.skill "
            + "WHERE v.id_vacancy=?";

    public CandidateDao(Connection connection) {
        super(connection);

        sqlSelectSortedPage = "SELECT * FROM candidate ORDER BY %s %s LIMIT ?, ?";
        sqlInsert = "INSERT INTO candidate "
                + "(name, surname, birthday, salary_in_dollars, candidate_state) "
                + "VALUES (?, ?, ?, ?, ?)";
        sqlUpdate = "UPDATE candidate "
                + "SET name=?, surname=?, birthday=?, salary_in_dollars=?, candidate_state=? "
                + "WHERE id=?";
        sqlSelectFilteredEntities = "SELECT * FROM candidate "
                + "WHERE (name=? OR ?='') AND (surname=? OR ?='') AND "
                + "(birthday=? OR ?='') AND (salary_in_dollars=? OR ?='') "
                + "AND (candidate_state=? OR ?='')";
        sqlSelectById = "SELECT * FROM candidate AS c WHERE c.id=?";
    }


    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        int parameterIndex = 1;
        prepareStatement.setString(parameterIndex++, candidate.getName());
        prepareStatement.setString(parameterIndex++, candidate.getSurname());
        prepareStatement.setDate(parameterIndex++, candidate.getBirthday(),
                Calendar.getInstance());
        prepareStatement.setFloat(parameterIndex++, candidate.getSalaryInDollars());
        prepareStatement.setString(parameterIndex, candidate.getCandidateState());
    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        int parameterIndex = 6;
        setValuesForInsertIntoPrepareStatement(prepareStatement, candidate);
        prepareStatement.setLong(parameterIndex, candidate.getId());

    }

    @Override
    protected Candidate setEntityFields(ResultSet candidateTableRow) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(candidateTableRow.getLong("id"));
        candidate.setName(candidateTableRow.getString("name"));
        candidate.setSurname(candidateTableRow.getString("surname"));
        candidate.setBirthday(candidateTableRow.getDate("birthday"));
        candidate.setSalaryInDollars(candidateTableRow.getFloat("salary_in_dollars"));
        candidate.setCandidateState(candidateTableRow.getString("candidate_state"));
        return candidate;
    }

    @Override
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement, Candidate entity)
            throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
