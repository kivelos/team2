package dev.java.db.DAOs;

import dev.java.db.model.Table;
import dev.java.db.model.Vacancy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VacancyDAO extends AbstractDAO<Vacancy> {
    public VacancyDAO(Connection connection, Table table) {
        super(connection, table);
    }

    @Override
    public boolean delete(Vacancy entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM " + table + " WHERE id='" + entity.getId() + "'";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean create(Vacancy entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO " + table +
                    " (name, min_experience_in_years, max_experience_in_years, " +
                    "min_salary_in_dollars, max_salary_in_dollars) " +
                    "VALUES ('";
            sql += entity.getName() + "', '" +
                    entity.getMinExperienceInYears() + "', '" +
                    entity.getMaxExperienceInYears() + "', '" +
                    entity.getMinSalaryInDollars() + "', '" +
                    entity.getMaxSalaryInDollars()  + "')";
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    public boolean update(Vacancy entity) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "UPDATE " + table +" SET " +
                    "name='" + entity.getName() + "', " +
                    "min_experience_in_years='" + entity.getMinExperienceInYears() + "', " +
                    "max_experience_in_years='" + entity.getMaxExperienceInYears() + "', " +
                    "min_salary_in_dollars='" + entity.getMinSalaryInDollars() + "', " +
                    "max_salary_in_dollars='" + entity.getMaxSalaryInDollars() + "'" +
                    "WHERE id=" + entity.getId();
            return statement.executeUpdate(sql) != 0;
        }
    }

    @Override
    protected void setFields(ResultSet resultSet, Vacancy vacancy) throws SQLException {
        vacancy.setId(resultSet.getLong("id"));
        vacancy.setName(resultSet.getString("name"));
        vacancy.setMinExperienceInYears(resultSet.getInt("min_experience_in_years"));
        vacancy.setMaxExperienceInYears(resultSet.getInt("max_experience_in_years"));
        vacancy.setMinSalaryInDollars(resultSet.getInt("min_salary_in_dollars"));
        vacancy.setMaxSalaryInDollars(resultSet.getInt("max_salary_in_dollars"));
    }
}
