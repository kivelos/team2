package dev.java.db.daos;

import dev.java.db.model.FeedbackState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FeedbackStateDao {
    private Connection connection;

    public FeedbackStateDao(Connection connection) {
        this.connection = connection;
    }

    public List<FeedbackState> getSortedEntitiesPage() throws SQLException {
        List<FeedbackState> states = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet state = statement.executeQuery("SELECT * FROM feedback_state");
            while (state.next()) {
                states.add(new FeedbackState(state.getString("name")));
            }
            state.close();
        }
        return states;
    }
}
