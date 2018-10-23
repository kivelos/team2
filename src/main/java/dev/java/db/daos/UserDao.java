package dev.java.db.daos;

import dev.java.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends AbstractDao<User> {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    protected User setEntityFields(ResultSet entityTableRow) throws SQLException {
        return null;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, User entity) throws SQLException {

    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, User entity) throws SQLException {

    }
}
