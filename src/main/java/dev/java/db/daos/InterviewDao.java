package dev.java.db.daos;

import dev.java.db.model.Interview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class InterviewDao extends AbstractDao<Interview> {
    private static String SQL_SELECT_BY_ID = "SELECT * FROM interview AS c WHERE c.id=?";

    public InterviewDao(Connection connection) {
        super(connection);
        SQL_SELECT_SORTED_PAGE = "SELECT * FROM interview ORDER BY %s %s LIMIT ?, ?";
        SQL_INSERT = "INSERT INTO interview "
                + "(id_candidate, id_vacancy, plan_date, fact_date) "
                + "VALUES (?, ?, ?, ?)";
        SQL_UPDATE = "UPDATE interview "
                + "SET id_candidate=?, id_vacancy=?, plan_date=?, fact_date=? "
                + "WHERE id=?";
        SQL_SELECT_FILTERED_ENTITIES = "SELECT * FROM interview "
                + "WHERE (id_candidate=? OR ?='') AND (id_vacancy=? OR ?='') AND (plan_date=? OR ?='')AND (fact_date=? OR ?='')";
    }

    @Override
    public List<Interview> getSortedEntitiesPage(int pageNumber, String sortedField, boolean order, int itemsNumberInPage) throws SQLException {
        List<Interview> interviews = super.getSortedEntitiesPage(pageNumber, sortedField, order, itemsNumberInPage);
        CandidateDao candidateDao = new CandidateDao(connection);
        for (Interview interview: interviews) {
            interview.setCandidate(candidateDao.getEntityById(interview.getCandidate().getId()));
        }
        VacancyDao vacancyDao = new VacancyDao(connection);
        for (Interview interview: interviews) {
            interview.setVacancy(vacancyDao.getEntityById(interview.getVacancy().getId()));
        }
        return interviews;
    }

    @Override
    protected Interview setEntityFields(ResultSet entityTableRow) throws SQLException {
        Interview interview = new Interview();
        interview.setId(entityTableRow.getLong("id"));
        CandidateDao candidateDao = new CandidateDao(connection);
        interview.setCandidate(candidateDao.getEntityById(entityTableRow.
                getLong("id_candidate")));
        VacancyDao vacancyDao = new VacancyDao(connection);
        interview.setVacancy(vacancyDao.getEntityById(entityTableRow.
                getLong("id_vacancy")));
        interview.setPlanDate(entityTableRow.getTimestamp("plan_date"));
        interview.setFactDate(entityTableRow.getTimestamp("fact_date"));
        return interview;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Interview entity)
            throws SQLException {
        prepareStatement.setLong(1, entity.getCandidate().getId());
        prepareStatement.setLong(2, entity.getVacancy().getId());
        prepareStatement.setTimestamp(3, entity.getPlanDate(), Calendar.getInstance());
        prepareStatement.setTimestamp(4, entity.getFactDate(), Calendar.getInstance());

    }

    @Override
    protected final void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement,
                                                                Interview entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }

    public final Interview getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement =
                     connection.prepareStatement(SQL_SELECT_BY_ID)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                Interview interview = setEntityFields(entity);
                entity.close();
                return interview;
            }
            return null;
        }
    }
}
