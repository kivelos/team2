package dev.java.db.daos;

import dev.java.db.model.CandidateState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class CandidateStateDao {
    private Connection connection;
    public CandidateStateDao(Connection connection) {
        this.connection = connection;
    }

    public List<CandidateState> getSortedEntitiesPage() throws SQLException {
        List<CandidateState> states = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet state = statement.executeQuery("SELECT * FROM candidate_state");
            while (state.next()) {
                states.add(new CandidateState(state.getString("name")));
            }
            state.close();
        }
        return states;
    }

}
