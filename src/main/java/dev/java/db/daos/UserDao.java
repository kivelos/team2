package dev.java.db.daos;

import dev.java.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends AbstractDao<User> {

    private static String SQL_SELECT_BY_ID = "SELECT * FROM user AS c WHERE c.id=?";

    public UserDao(Connection connection) {
        super(connection);
        SQL_SELECT_SORTED_PAGE = "SELECT * FROM user ORDER BY %s %s LIMIT ?, ?";
        SQL_INSERT = "INSERT INTO user "
                + "(email,password, surname, name, user_state) "
                + "VALUES (?, ?, ?, ?, ?)";
        SQL_UPDATE = "UPDATE user "
                + "SET email=?, password=?, surname=?, name=?, user_state=? "
                + "WHERE id=?";
        SQL_SELECT_FILTERED_ENTITIES = "SELECT * FROM user "
                + "WHERE (email=? OR ?='') AND (password=? OR ?='') AND "
                + "(name=? OR ?='') AND (surname=? OR ?='') "
                + "AND (user_state=? OR ?='')";
    }

    public final User getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                User user = setEntityFields(entity);
                entity.close();
                return user;
            }
            return null;
        }
    }

    @Override
    protected final User setEntityFields(ResultSet entityTableRow) throws SQLException {
        User user = new User();
        user.setId(entityTableRow.getLong("id"));
        user.setEmail(entityTableRow.getString("email"));
        user.setSurname(entityTableRow.getString("surname"));
        user.setName(entityTableRow.getString("name"));
        user.setPassword(entityTableRow.getString("password"));
        user.setState(User.State.valueOf(entityTableRow.getString("user_state")));
        return user;
    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, User user) throws SQLException {
        prepareStatement.setString(1, user.getEmail());
        prepareStatement.setString(2, user.getPassword());
        prepareStatement.setString(3, user.getSurname());
        prepareStatement.setString(4, user.getName());
        prepareStatement.setString(5, user.getState().name());
    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, User entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(6, entity.getId());
    }
}
