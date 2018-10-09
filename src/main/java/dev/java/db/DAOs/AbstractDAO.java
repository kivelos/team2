package dev.java.db.DAOs;

import dev.java.db.model.Entity;
import dev.java.db.model.EntityFactory;
import dev.java.db.model.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO <T extends Entity> {
    protected Connection connection;
    protected Table table;
    public AbstractDAO(Connection connection, Table table) {
        this.connection = connection;
        this.table = table;
    }

    public List<T> findAll() throws SQLException {
        List<T> candidates = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
            while (resultSet.next()) {
                T entity = (T)EntityFactory.getEntity(table);
                setFields(resultSet, entity);
                candidates.add(entity);
            }
            return candidates;
        }
    }
    public T findEntityById(int id) throws SQLException {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table +" WHERE id='" + id + "'");
            resultSet.next();
            T entity = (T)EntityFactory.getEntity(table);
            setFields(resultSet, entity);
            return entity;
        }
    }
    /*public T findEntityByFiled(String field, String value) throws SQLException {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table +" WHERE " + field + "='" + value + "'");
            resultSet.next();
            T entity = (T)EntityFactory.getEntity(table);
            setFields(resultSet, entity);
            return entity;
        }
    }*/
    public boolean delete(int id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM " + table + " WHERE id=" + id;
            return statement.executeUpdate(sql) != 0;
        }
    }
    public abstract boolean delete(T entity) throws SQLException;
    public abstract boolean create(T entity) throws SQLException;
    public abstract boolean update(T entity) throws SQLException;

    protected abstract void setFields(ResultSet resultSet, T t) throws SQLException;

}
