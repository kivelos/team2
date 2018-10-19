package dev.java.db.daos;

import dev.java.db.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDao extends AbstractDao<Candidate> {
    private final static String SQL_INSERT =
            "INSERT INTO candidate " +
                    "(name, surname, birthday, salary_in_dollars, candidate_state) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private final static String SQL_UPDATE =
            "UPDATE candidate " +
                    "SET name=?, surname=?, birthday=?, salary_in_dollars=?, candidate_state=?" +
                    "WHERE id=?";

    private final static String SQL_SELECT_BY_ID = "SELECT * FROM candidate AS c WHERE c.id=?";

    private final static String SQL_SELECT_ALL = "SELECT * FROM candidate";

    public CandidateDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Candidate> getAllEntities() throws SQLException {
        List<Candidate> allCandidatesList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet candidateTableRow = statement.executeQuery(SQL_SELECT_ALL);
            while (candidateTableRow.next()) {
                Candidate candidate = new Candidate();
                setCandidateFields(candidateTableRow, candidate);
                allCandidatesList.add(candidate);
            }
            candidateTableRow.close();
        }
        return allCandidatesList;
    }

    @Override
    public boolean createEntity(Candidate entity) throws SQLException {
        try (PreparedStatement insertPrepareStatement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status =  insertPrepareStatement.executeUpdate();
            if (status > 0) {
                ResultSet id = insertPrepareStatement.getGeneratedKeys();
                if (id.next()) {
                    entity.setId(id.getLong(1));
                    id.close();
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    @Override
    public boolean updateEntity(Candidate entity) throws SQLException {
        try (PreparedStatement updatePrepareStatement = connection.prepareStatement(SQL_UPDATE)) {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        }
    }

    public Candidate getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                Candidate candidate = new Candidate();
                setCandidateFields(entity, candidate);
                entity.close();
                return candidate;
            }
            return null;
        }
    }

    private void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        prepareStatement.setString(1, candidate.getName());
        prepareStatement.setString(2, candidate.getSurname());
        prepareStatement.setDate(3, candidate.getBirthday());
        prepareStatement.setFloat(4, candidate.getSalaryInDollars());
        prepareStatement.setString(5, candidate.getCandidateState());
    }

    private void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Candidate candidate)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, candidate);
        prepareStatement.setLong(6, candidate.getId());

    }

    private void setCandidateFields(ResultSet candidateTableRow, Candidate candidate) throws SQLException {
        candidate.setId(candidateTableRow.getLong("id"));
        candidate.setName(candidateTableRow.getString("name"));
        candidate.setSurname(candidateTableRow.getString("surname"));
        candidate.setBirthday(candidateTableRow.getDate("birthday"));
        candidate.setSalaryInDollars(candidateTableRow.getFloat("salary_in_dollars"));
        candidate.setCandidateState(candidateTableRow.getString("candidate_state"));
    }
}
