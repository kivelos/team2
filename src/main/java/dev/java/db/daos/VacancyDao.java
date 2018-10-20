package dev.java.db.daos;

import dev.java.db.model.Vacancy;
import dev.java.db.model.VacancyState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacancyDao extends AbstractDao<Vacancy> {
    private final static String SQL_INSERT =
            "INSERT INTO vacancy " +
                    "(position, salary_in_dollars_from, salary_in_dollars_to, " +
                    "vacancy_state, experience_years_require, id_developer) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private final static String SQL_UPDATE =
            "UPDATE vacancy " +
                    "SET position=?, salary_in_dollars_from=?, salary_in_dollars_to=?, " +
                    "vacancy_state=?, experience_years_require=?, id_developer=? " +
                    "WHERE id=?";

    private final static String SQL_SELECT_BY_ID = "SELECT * FROM vacancy AS v WHERE v.id=?";

    private final static String SQL_SELECT_ALL = "SELECT * FROM vacancy";

    public VacancyDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Vacancy> getAllEntities() throws SQLException {
        List<Vacancy> allVacanciesList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet vacancyTableRow = statement.executeQuery(SQL_SELECT_ALL);
            while (vacancyTableRow.next()) {
                Vacancy vacancy = new Vacancy();
                setVacancyFields(vacancyTableRow, vacancy);
                allVacanciesList.add(vacancy);
            }
            vacancyTableRow.close();
        }
        return allVacanciesList;
    }

    @Override
    public boolean createEntity(Vacancy entity) throws SQLException {
        try (PreparedStatement insertPrepareStatement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS)) {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status =  insertPrepareStatement.executeUpdate();
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
                Vacancy vacancy = new Vacancy();
                setVacancyFields(entity, vacancy);
                entity.close();
                return vacancy;
            }
            return null;
        }
    }

    private void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, vacancy);
        prepareStatement.setLong(7, vacancy.getId());

    }

    private void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        prepareStatement.setString(1, vacancy.getPosition());
        prepareStatement.setFloat(2, vacancy.getSalaryInDollarsFrom());
        prepareStatement.setFloat(3, vacancy.getSalaryInDollarsTo());
        prepareStatement.setString(4, vacancy.getVacancyState().name());
        prepareStatement.setFloat(5, vacancy.getExperienceYearsRequire());
        prepareStatement.setLong(6, vacancy.getDeveloper().getId());
    }

    private void setVacancyFields(ResultSet vacancyTableRow, Vacancy vacancy) throws SQLException {
        vacancy.setId(vacancyTableRow.getLong("id"));
        vacancy.setPosition(vacancyTableRow.getString("position"));
        vacancy.setSalaryInDollarsFrom(vacancyTableRow.getFloat("salary_in_dollars_from"));
        vacancy.setSalaryInDollarsTo(vacancyTableRow.getFloat("salary_in_dollars_to"));
        vacancy.setVacancyState(VacancyState.valueOf(vacancyTableRow.getString("vacancy_state")));
        vacancy.setExperienceYearsRequire(vacancyTableRow.getFloat("experience_years_require"));
    }
}
