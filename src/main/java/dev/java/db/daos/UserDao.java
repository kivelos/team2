package dev.java.db.daos;

import dev.java.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends AbstractDao<User> {


    public UserDao(Connection connection) {
        super(connection);
        setSqlSelectSortedPage("SELECT * FROM user ORDER BY %s %s LIMIT ?, ?");
        setSqlInsert("INSERT INTO user "
                + "(email,password, surname, name, user_state) "
                + "VALUES (?, ?, ?, ?, ?)");
        setSqlUpdate("UPDATE user "
                + "SET email=?, password=?, surname=?, name=?, user_state=? "
                + "WHERE id=?");
        setSqlSelectFilteredEntities("SELECT * FROM user "
                + "WHERE (email=? OR ?='') AND (password=? OR ?='') AND "
                + "(name=? OR ?='') AND (surname=? OR ?='') "
                + "AND (user_state=? OR ?='')");
        setSqlSelectById("SELECT * FROM user AS c WHERE c.id=?");
    }

    @Override
    protected User setEntityFields(ResultSet entityTableRow) throws SQLException {
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
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, User user)
            throws SQLException {
        prepareStatement.setString(1, user.getEmail());
        prepareStatement.setString(2, user.getPassword());
        prepareStatement.setString(3, user.getSurname());
        prepareStatement.setString(4, user.getName());
        prepareStatement.setString(5, user.getState().name());
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          User entity) throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, entity);
        prepareStatement.setLong(6, entity.getId());
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement,
                                                          User entity) throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
