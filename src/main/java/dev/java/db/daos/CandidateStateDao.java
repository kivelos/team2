package dev.java.db.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateStateDao {
    private Connection connection;

    public CandidateStateDao(Connection connection) {
        this.connection = connection;
    }

    public List<String> getAllCandidateStates() throws SQLException {
        List<String> states = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet state = statement.executeQuery("SELECT * FROM candidate_state");
            while (state.next()) {
                states.add(state.getString("name"));
            }
            state.close();
        }
        return states;
    }
}
