package dev.java.db.daos;

import dev.java.db.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class CandidateDao extends AbstractDao<Candidate> {

    //language=SQL
    /*private String sqlSelectSkills = "SELECT skill.name FROM skill "
            + "JOIN vacancy_requirement v on skill.name = v.skill "
            + "WHERE v.id_vacancy=?";
    */
    public CandidateDao(Connection connection) {
        super(connection);
        setSqlSelectSortedPage("SELECT * FROM candidate ORDER BY %s %s LIMIT ?, ?");
        setSqlInsert("INSERT INTO candidate "
                + "(name, surname, birthday, salary_in_dollars, candidate_state) "
                + "VALUES (?, ?, ?, ?, ?)");
        setSqlUpdate("UPDATE candidate "
                + "SET name=?, surname=?, birthday=?, salary_in_dollars=?, candidate_state=? "
                + "WHERE id=?");
        setSqlSelectFilteredEntities("SELECT * FROM candidate "
                + "WHERE (name=? OR ?='') AND (surname=? OR ?='') AND "
                + "(birthday=? OR ?='') AND (salary_in_dollars=? OR ?='') "
                + "AND (candidate_state=? OR ?='')");
        setSqlSelectById("SELECT * FROM candidate AS c WHERE c.id=?");
    }


    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        prepareStatement.setString(1, candidate.getName());
        prepareStatement.setString(2, candidate.getSurname());
        prepareStatement.setDate(3, candidate.getBirthday(),
                Calendar.getInstance());
        prepareStatement.setFloat(4, candidate.getSalaryInDollars());
        prepareStatement.setString(5, candidate.getCandidateState());
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, candidate);
        prepareStatement.setLong(6, candidate.getId());

    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
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
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement, Candidate entity)
            throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
