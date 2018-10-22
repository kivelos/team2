package dev.java.db.daos;

import dev.java.db.model.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;
    protected static String SQL_SELECT_SORTED_PAGE;
    protected static String SQL_INSERT;
    protected static String SQL_UPDATE;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public List<T> getSortedEntitiesPage(int pageNumber, String sortedField, int itemsNumberInPage) throws SQLException {
        List<T> allEntitiesList = new ArrayList<>();
        try (PreparedStatement selectPrepareStatement = connection.prepareStatement(SQL_SELECT_SORTED_PAGE)) {
            selectPrepareStatement.setString(1, sortedField);
            selectPrepareStatement.setInt(2, (pageNumber - 1) * itemsNumberInPage);
            selectPrepareStatement.setInt(3, itemsNumberInPage);
            ResultSet entityTableRow = selectPrepareStatement.executeQuery();
            while (entityTableRow.next()) {
                T entity = setEntityFields(entityTableRow);
                allEntitiesList.add(entity);
            }
            entityTableRow.close();
        }
        return allEntitiesList;
    }
    public boolean createEntity(T entity) throws SQLException {
        try (PreparedStatement insertPrepareStatement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status =  insertPrepareStatement.executeUpdate();
            if (status > 0) {
                ResultSet id = insertPrepareStatement.getGeneratedKeys();
                if (id.next()) {
                    entity.setId(id.getLong("id"));
                    id.close();
                    return true;
                }
                return false;
            }
            return false;
        }
    }
    public boolean updateEntity(T entity) throws SQLException {
        try (PreparedStatement updatePrepareStatement = connection.prepareStatement(SQL_UPDATE)) {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        }
    }

    protected abstract T setEntityFields(ResultSet entityTableRow) throws SQLException;
    protected abstract void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, T entity)
        throws SQLException;
    protected abstract void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, T entity)
            throws SQLException;


}
