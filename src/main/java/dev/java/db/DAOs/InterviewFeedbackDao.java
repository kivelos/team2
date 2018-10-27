package dev.java.db.daos;

import dev.java.db.model.Interview;
import dev.java.db.model.InterviewFeedback;
import dev.java.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InterviewFeedbackDao extends AbstractDao<InterviewFeedback> {
    public InterviewFeedbackDao(Connection connection) {
        super(connection);
        SQL_SELECT_SORTED_PAGE = "SELECT * FROM interview_feedback ORDER BY %s %s LIMIT ?, ?";
        SQL_INSERT = "INSERT INTO interview_feedback " +
                "(id_interview, id_interviewer, reason, feedback_state) " +
                "VALUES (?, ?, ?, ?)";
        SQL_UPDATE = "UPDATE interview_feedback " +
                "SET id_interview=?, id_interviewer=?, reason=?, feedback_state=? " +
                "WHERE id=?";
        SQL_SELECT_FILTERED_ENTITIES = "SELECT * FROM interview_feedback " +
                "WHERE id_interview=? AND id_interviewer=? AND reason=? AND feedback_state=?";
    }

    @Override
    protected InterviewFeedback setEntityFields(ResultSet entityTableRow) throws SQLException {
        InterviewFeedback interviewFeedback = new InterviewFeedback();
        interviewFeedback.setId(entityTableRow.getLong("id"));
        interviewFeedback.setInterview(new Interview(entityTableRow.getLong("id_interview")));
        interviewFeedback.setInterviewer(new User(entityTableRow.getLong("id_interviewer")));
        interviewFeedback.setReason(entityTableRow.getString("reason"));
        interviewFeedback.setFeedbackState(entityTableRow.getString("feedback_state"));
        return interviewFeedback;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, InterviewFeedback entity)
            throws SQLException {
        prepareStatement.setLong(1, entity.getInterview().getId());
        prepareStatement.setLong(2, entity.getInterviewer().getId());
        prepareStatement.setString(3, entity.getReason());
        prepareStatement.setString(4, entity.getFeedbackState());

    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, InterviewFeedback entity)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(5, entity.getId());
    }
}
