package dev.java.db.daos;

import dev.java.db.model.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    private Connection connection;
    //language=SQL
    private String sqlSelectSortedPage = "";
    //language=SQL
    private String sqlInsert = "";
    //language=SQL
    private String sqlUpdate = "";
    //language=SQL
    private String sqlSelectFilteredEntities = "";
    //language=SQL
    private String sqlDelete = "";
    //language=SQL
    private String sqlSelectById = "";

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public List<T> getSortedEntitiesPage(int pageNumber, String sortedField, boolean order, int itemsNumberInPage)
            throws SQLException {
        List<T> allEntitiesList = new ArrayList<>();
        String orderType = "ASC";
        if (!order) {
            orderType = "DESC";
        }
        sqlSelectSortedPage = String.format(sqlSelectSortedPage, sortedField, orderType);
        PreparedStatement selectPrepareStatement = connection.prepareStatement(sqlSelectSortedPage);
        try  {
            selectPrepareStatement.setInt(1, (pageNumber - 1) * itemsNumberInPage);
            selectPrepareStatement.setInt(2, itemsNumberInPage);
            ResultSet entityTableRow = selectPrepareStatement.executeQuery();
            while (entityTableRow.next()) {
                T entity = setEntityFields(entityTableRow);
                allEntitiesList.add(entity);
            }
            entityTableRow.close();
        } finally {
            selectPrepareStatement.close();
        }
        return allEntitiesList;
    }

    public List<T> getFilteredEntitiesPage(String... params)
            throws SQLException {
        List<T> allEntitiesList = new ArrayList<>();
        PreparedStatement selectPrepareStatement = connection.prepareStatement(sqlSelectFilteredEntities);
        try {
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
        } finally {
            selectPrepareStatement.close();
        }
        return allEntitiesList;
    }

    public boolean createEntity(T entity) throws SQLException {
        PreparedStatement insertPrepareStatement = connection.prepareStatement(sqlInsert,
                Statement.RETURN_GENERATED_KEYS);
        try {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status = insertPrepareStatement.executeUpdate();
            if (status <= 0) {
                return false;
            }

            ResultSet id = insertPrepareStatement.getGeneratedKeys();
            if (!id.next()) {
                return false;
            }

            entity.setId(id.getLong(1));
            id.close();
            return true;
        } finally {
            insertPrepareStatement.close();
        }
    }

    public boolean updateEntity(T entity) throws SQLException {
        PreparedStatement updatePrepareStatement = connection.prepareStatement(sqlUpdate);
        try {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        } finally {
            updatePrepareStatement.close();
        }
    }

    public boolean deleteEntity(T entity) throws SQLException {
        PreparedStatement deletePrepareStatement = connection.prepareStatement(sqlDelete);
        try {
            setValuesForDeleteIntoPrepareStatement(deletePrepareStatement, entity);
            return deletePrepareStatement.executeUpdate() > 0;
        } finally {
            deletePrepareStatement.close();
        }
    }

    public T getEntityById(long id) throws SQLException {
        PreparedStatement getByIdPrepareStatement = connection.prepareStatement(sqlSelectById);
        try {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                T newEntity = setEntityFields(entity);
                entity.close();
                return newEntity;
            }
            return null;
        } finally {
            getByIdPrepareStatement.close();
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getSqlSelectSortedPage() {
        return sqlSelectSortedPage;
    }

    public void setSqlSelectSortedPage(String sqlSelectSortedPage) {
        this.sqlSelectSortedPage = sqlSelectSortedPage;
    }

    public String getSqlInsert() {
        return sqlInsert;
    }

    public void setSqlInsert(String sqlInsert) {
        this.sqlInsert = sqlInsert;
    }

    public String getSqlUpdate() {
        return sqlUpdate;
    }

    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    public String getSqlSelectFilteredEntities() {
        return sqlSelectFilteredEntities;
    }

    public void setSqlSelectFilteredEntities(String sqlSelectFilteredEntities) {
        this.sqlSelectFilteredEntities = sqlSelectFilteredEntities;
    }

    public String getSqlDelete() {
        return sqlDelete;
    }

    public void setSqlDelete(String sqlDelete) {
        this.sqlDelete = sqlDelete;
    }

    public String getSqlSelectById() {
        return sqlSelectById;
    }

    public void setSqlSelectById(String sqlSelectById) {
        this.sqlSelectById = sqlSelectById;
    }
}
