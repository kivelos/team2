package dev.java.db.daos;

import dev.java.db.model.InterviewFeedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class InterviewFeedbackDao extends AbstractDao<InterviewFeedback> {

    public InterviewFeedbackDao(Connection connection) {
        super(connection);
        sqlSelectSortedPage = "SELECT * FROM interview_feedback "
                + "ORDER BY %s %s LIMIT ?, ?";
        sqlInsert = "INSERT INTO interview_feedback "
                + "(id_interview, id_interviewer, reason, feedback_state) "
                + "VALUES (?, ?, ?, ?)";
        sqlUpdate = "UPDATE interview_feedback "
                + "SET id_interview=?, id_interviewer=?, reason=?, feedback_state=? "
                + "WHERE id=?";
        sqlSelectFilteredEntities = "SELECT * FROM interview_feedback "
                + "WHERE (id_interview=? OR ?='') AND (id_interviewer=? OR ?='') "
                + "AND (reason=? OR ?='') AND (feedback_state=? OR ?='')";
        sqlSelectById = "SELECT * FROM interview_feedback "
                + "AS c WHERE c.id=?";
    }

    @Override
    public  List<InterviewFeedback> getSortedEntitiesPage(int pageNumber,
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
    protected  InterviewFeedback setEntityFields(ResultSet entityTableRow) throws SQLException {
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
    protected  void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          InterviewFeedback entity) throws SQLException {
        prepareStatement.setLong(1, entity.getInterview().getId());
        prepareStatement.setLong(2, entity.getInterviewer().getId());
        prepareStatement.setString(3, entity.getReason());
        prepareStatement.setString(4, entity.getFeedbackState());

    }

    @Override
    protected  void setValuesForUpdateIntoPrepareStatement
            (PreparedStatement prepareStatement, InterviewFeedback entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }


    @Override
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement, 
                                                          InterviewFeedback entity) throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
