package dev.java.db.DAOs;

import dev.java.db.model.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO <T extends Entity> {
    protected Connection connection;
    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws SQLException;
    public abstract T findEntityById(int id) throws SQLException;
    public abstract T findEntityByFiled(String field, String value) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;
    public abstract boolean delete(T entity) throws SQLException;
    public abstract T create(T entity) throws SQLException;
    public abstract boolean update(T entity) throws SQLException;

}
