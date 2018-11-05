package dev.java.db.daos;

import dev.java.db.model.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;
    //language=SQL
    protected String sqlSelectSortedPage;
    //language=SQL
    protected String sqlInsert;
    //language=SQL
    protected String sqlUpdate;
    //language=SQL
    protected String sqlSelectFilteredEntities;
    //language=SQL
    protected String sqlDelete;
    //language=SQL
    protected String sqlSelectById;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public List<T> getSortedEntitiesPage(int pageNumber, String sortedField, boolean order, int itemsNumberInPage)
            throws SQLException {
        List<T> allEntitiesList = new ArrayList<>();
        sqlSelectSortedPage = String.format(sqlSelectSortedPage, sortedField,
                order ? "ASC" : "DESC");
        try (PreparedStatement selectPrepareStatement = connection.prepareStatement(sqlSelectSortedPage)) {
            selectPrepareStatement.setInt(1, (pageNumber - 1) * itemsNumberInPage);
            selectPrepareStatement.setInt(2, itemsNumberInPage);
            ResultSet entityTableRow = selectPrepareStatement.executeQuery();
            while (entityTableRow.next()) {
                T entity = setEntityFields(entityTableRow);
                allEntitiesList.add(entity);
            }
            entityTableRow.close();
        }
        return allEntitiesList;
    }

    public List<T> getFilteredEntitiesPage(String... params)
            throws SQLException {
        List<T> allEntitiesList = new ArrayList<>();
        try (PreparedStatement selectPrepareStatement = connection.prepareStatement(sqlSelectFilteredEntities)) {
            for (int i = 0; i < params.length; i++) {
                selectPrepareStatement.setString(i * 2 + 1, params[i]);
                selectPrepareStatement.setString(i * 2 + 2, params[i]);
            }
            System.out.println(selectPrepareStatement);
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
        try (PreparedStatement insertPrepareStatement = connection.prepareStatement(sqlInsert,
                Statement.RETURN_GENERATED_KEYS)) {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status = insertPrepareStatement.executeUpdate();
            if (status > 0) {
                ResultSet id = insertPrepareStatement.getGeneratedKeys();
                if (id.next()) {
                    entity.setId(id.getLong(1));
                    id.close();
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    public boolean updateEntity(T entity) throws SQLException {
        try (PreparedStatement updatePrepareStatement
                     = connection.prepareStatement(sqlUpdate)) {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        }
    }

    public boolean deleteEntity(T entity) throws SQLException {
        try (PreparedStatement deletePrepareStatement
                     = connection.prepareStatement(sqlDelete)) {
            setValuesForDeleteIntoPrepareStatement(deletePrepareStatement, entity);
            return deletePrepareStatement.executeUpdate() > 0;
        }
    }

    public T getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(sqlSelectById)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                T newEntity = setEntityFields(entity);
                entity.close();
                return newEntity;
            }
            return null;
        }
    }

    protected abstract T setEntityFields(ResultSet entityTableRow) throws SQLException;

    protected abstract void setValuesForInsertIntoPrepareStatement(
            PreparedStatement prepareStatement, T entity)
            throws SQLException;

    protected abstract void setValuesForUpdateIntoPrepareStatement(
            PreparedStatement prepareStatement, T entity)
            throws SQLException;

    protected abstract void setValuesForDeleteIntoPrepareStatement(
            PreparedStatement prepareStatement, T entity)
            throws SQLException;

}
