package dev.java.db.daos;

import dev.java.db.model.Vacancy;
import dev.java.db.model.VacancyState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyDao extends AbstractDao<Vacancy> {

    private static String SQL_SELECT_BY_ID = "SELECT * FROM vacancy AS v WHERE v.id=?";

    public VacancyDao(Connection connection) {
        super(connection);
        SQL_SELECT_SORTED_PAGE = "SELECT * FROM vacancy AS c ORDER BY ? ASC LIMIT ?, ?";
        SQL_INSERT = "INSERT INTO vacancy " +
                "(position, salary_in_dollars_from, salary_in_dollars_to, " +
                "vacancy_state, experience_years_require, id_developer) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        SQL_UPDATE = "UPDATE vacancy " +
                "SET position=?, salary_in_dollars_from=?, salary_in_dollars_to=?, " +
                "vacancy_state=?, experience_years_require=?, id_developer=? " +
                "WHERE id=?";
    }


    @Override
    public boolean updateEntity(Vacancy entity) throws SQLException {
        try (PreparedStatement updatePrepareStatement = connection.prepareStatement(SQL_UPDATE)) {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        }
    }

    public Vacancy getEntityById(long id) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            getByIdPrepareStatement.setLong(1, id);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                Vacancy vacancy = setEntityFields(entity);
                entity.close();
                return vacancy;
            }
            return null;
        }
    }

    @Override
    protected void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, vacancy);
        prepareStatement.setLong(7, vacancy.getId());

    }

    @Override
    protected void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        prepareStatement.setString(1, vacancy.getPosition());
        prepareStatement.setFloat(2, vacancy.getSalaryInDollarsFrom());
        prepareStatement.setFloat(3, vacancy.getSalaryInDollarsTo());
        prepareStatement.setString(4, vacancy.getVacancyState().name());
        prepareStatement.setFloat(5, vacancy.getExperienceYearsRequire());
        prepareStatement.setLong(6, vacancy.getDeveloper().getId());
    }

    @Override
    protected Vacancy setEntityFields(ResultSet vacancyTableRow) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyTableRow.getLong("id"));
        vacancy.setPosition(vacancyTableRow.getString("position"));
        vacancy.setSalaryInDollarsFrom(vacancyTableRow.getFloat("salary_in_dollars_from"));
        vacancy.setSalaryInDollarsTo(vacancyTableRow.getFloat("salary_in_dollars_to"));
        vacancy.setVacancyState(VacancyState.valueOf(vacancyTableRow.getString("vacancy_state")));
        vacancy.setExperienceYearsRequire(vacancyTableRow.getFloat("experience_years_require"));
        return vacancy;
    }
}
