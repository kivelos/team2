package dev.java.db.daos;

import dev.java.db.model.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;
    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> getAllEntities() throws SQLException;
    public abstract boolean createEntity(T entity) throws SQLException;
    public abstract boolean updateEntity(T entity) throws SQLException;

}
