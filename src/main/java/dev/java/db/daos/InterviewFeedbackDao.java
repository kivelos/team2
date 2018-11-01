package dev.java.db.daos;

import dev.java.db.model.InterviewFeedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InterviewFeedbackDao extends AbstractDao<InterviewFeedback> {
    private static String SQL_SELECT_BY_ID = "SELECT * FROM inteview_feedback "
            + "AS c WHERE c.id=?";

    public InterviewFeedbackDao(Connection connection) {
        super(connection);
        SQL_SELECT_SORTED_PAGE = "SELECT * FROM inteview_feedback "
                + "ORDER BY %s %s LIMIT ?, ?";
        SQL_INSERT = "INSERT INTO inteview_feedback "
                + "(id_interview, id_interviewer, reason, feedback_state) "
                + "VALUES (?, ?, ?, ?)";
        SQL_UPDATE = "UPDATE inteview_feedback "
                + "SET id_interview=?, id_interviewer=?, reason=?, feedback_state=? "
                + "WHERE id=?";
        SQL_SELECT_FILTERED_ENTITIES = "SELECT * FROM inteview_feedback "
                + "WHERE (id_interview=? OR ?='') AND (id_interviewer=? OR ?='') "
                + "AND (reason=? OR ?='') AND (feedback_state=? OR ?='')";
    }

    @Override
    public final List<InterviewFeedback> getSortedEntitiesPage(int pageNumber,
           String sortedField, boolean order, int itemsNumberInPage) throws SQLException {
        List<InterviewFeedback> interviewFeedbacks = super.getSortedEntitiesPage(pageNumber,
                sortedField, order, itemsNumberInPage);
        InterviewDao interviewDao = new InterviewDao(connection);
        for (InterviewFeedback feedback: interviewFeedbacks) {
            feedback.setInterview(interviewDao.getEntityById(feedback.getInterview().getId()));
        }
        UserDao userDao = new UserDao(connection);
        for (InterviewFeedback feedback: interviewFeedbacks) {
            feedback.setInterviewer(userDao.getEntityById(feedback.getInterviewer().getId()));
        }
        return interviewFeedbacks;
    }

    @Override
    protected final InterviewFeedback setEntityFields(ResultSet entityTableRow) throws SQLException {
        InterviewFeedback interviewFeedback = new InterviewFeedback();
        interviewFeedback.setId(entityTableRow.getLong("id"));
        InterviewDao interviewDao=new InterviewDao(connection);
        interviewFeedback.setInterview(interviewDao.getEntityById(entityTableRow.getLong("id_interview")));
        UserDao userDao=new UserDao(connection);
        interviewFeedback.setInterviewer(userDao.getEntityById(entityTableRow.getLong("id_interviewer")));
        interviewFeedback.setReason(entityTableRow.getString("reason"));
        interviewFeedback.setFeedbackState(entityTableRow.getString("feedback_state"));
        return interviewFeedback;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          InterviewFeedback entity) throws SQLException {
        prepareStatement.setLong(1, entity.getInterview().getId());
        prepareStatement.setLong(2, entity.getInterviewer().getId());
        prepareStatement.setString(3, entity.getReason());
        prepareStatement.setString(4, entity.getFeedbackState());

    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          InterviewFeedback entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }

    public InterviewFeedback getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                InterviewFeedback interviewFeedback= setEntityFields(entity);
                entity.close();
                return interviewFeedback;
            }
            return null;
        }
    }
}
