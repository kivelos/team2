package dev.java.db.daos;

import dev.java.db.model.Interview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class InterviewDao extends AbstractDao<Interview> {

    public InterviewDao(Connection connection) {
        super(connection);
        setSqlSelectSortedPage("SELECT * FROM interview ORDER BY %s %s LIMIT ?, ?");
        setSqlInsert("INSERT INTO interview "
                + "(id_candidate, id_vacancy, plan_date, fact_date) "
                + "VALUES (?, ?, ?, ?)");
        setSqlUpdate("UPDATE interview "
                + "SET id_candidate=?, id_vacancy=?, plan_date=?, fact_date=? "
                + "WHERE id=?");
        setSqlSelectFilteredEntities("SELECT * FROM interview "
                + "WHERE (id_candidate=? OR ?='') AND (id_vacancy=? OR ?='') AND (plan_date=? OR ?='') "
                + "AND (fact_date=? OR ?='')");
        setSqlSelectById("SELECT * FROM interview AS c WHERE c.id=?");
    }

    @Override
    public List<Interview> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                 boolean order, int itemsNumberInPage) throws SQLException {
        List<Interview> interviews = super.getSortedEntitiesPage(pageNumber, sortedField, order, itemsNumberInPage);
        CandidateDao candidateDao = new CandidateDao(getConnection());
        for (Interview interview : interviews) {
            interview.setCandidate(candidateDao.getEntityById(interview.getCandidate().getId()));
        }
        VacancyDao vacancyDao = new VacancyDao(getConnection());
        for (Interview interview : interviews) {
            interview.setVacancy(vacancyDao.getEntityById(interview.getVacancy().getId()));
        }
        return interviews;
    }

    @Override
    protected Interview setEntityFields(ResultSet entityTableRow) throws SQLException {
        Interview interview = new Interview();
        interview.setId(entityTableRow.getLong("id"));
        CandidateDao candidateDao = new CandidateDao(getConnection());
        interview.setCandidate(candidateDao.getEntityById(entityTableRow.
                getLong("id_candidate")));
        VacancyDao vacancyDao = new VacancyDao(getConnection());
        interview.setVacancy(vacancyDao.getEntityById(entityTableRow.
                getLong("id_vacancy")));
        interview.setPlanDate(entityTableRow.getTimestamp("plan_date"));
        interview.setFactDate(entityTableRow.getTimestamp("fact_date"));
        return interview;
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Interview entity)
            throws SQLException {
        prepareStatement.setLong(1, entity.getCandidate().getId());
        prepareStatement.setLong(2, entity.getVacancy().getId());
        prepareStatement.setTimestamp(3, entity.getPlanDate(), Calendar.getInstance());
        prepareStatement.setTimestamp(4, entity.getFactDate(), Calendar.getInstance());

    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          Interview entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }


    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          Interview entity) throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
