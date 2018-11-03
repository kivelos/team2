package dev.java.db.daos;

import dev.java.db.model.User;
import dev.java.db.model.Vacancy;
import dev.java.db.model.VacancyState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class VacancyDao extends AbstractDao<Vacancy> {

    public VacancyDao(Connection connection) {
        super(connection);
        sqlSelectSortedPage = "SELECT * FROM vacancy ORDER BY %s %s LIMIT ?, ?";
        sqlInsert = "INSERT INTO vacancy " +
                "(position, salary_in_dollars_from, salary_in_dollars_to, "
                + "vacancy_state, experience_years_require, id_developer) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        sqlUpdate = "UPDATE vacancy " +
                "SET position=?, salary_in_dollars_from=?, salary_in_dollars_to=?, "
                + "vacancy_state=?, experience_years_require=?, id_developer=? "
                + "WHERE id=?";
        sqlSelectFilteredEntities = "SELECT * FROM vacancy "
                + "WHERE (position=? OR ?='')  AND (salary_in_dollars_from=? OR ?='') AND (salary_in_dollars_to=? OR ?='')"
                + "AND (vacancy_state=? OR ?='') AND (experience_years_require=? OR ?='') AND (id_developer=? OR ?='')";
        sqlSelectById = "SELECT * FROM vacancy AS v WHERE v.id=?";
    }

    @Override
    public  List<Vacancy> getSortedEntitiesPage(int pageNumber, String sortedField, boolean order, int itemsNumberInPage) throws SQLException {
        List<Vacancy> vacancies = super.getSortedEntitiesPage(pageNumber, sortedField, order, itemsNumberInPage);
        UserDao userDao = new UserDao(connection);
        for (Vacancy vacancy: vacancies) {
            vacancy.setDeveloper(userDao.getEntityById(vacancy.getDeveloper().getId()));
        }
        return vacancies;
    }

    @Override
    protected  void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, vacancy);
        prepareStatement.setLong(7, vacancy.getId());

    }

    @Override
    protected  void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Vacancy vacancy)
            throws SQLException {
        prepareStatement.setString(1, vacancy.getPosition());
        prepareStatement.setFloat(2, vacancy.getSalaryInDollarsFrom());
        prepareStatement.setFloat(3, vacancy.getSalaryInDollarsTo());
        prepareStatement.setString(4, vacancy.getVacancyState().name());
        prepareStatement.setFloat(5, vacancy.getExperienceYearsRequire());
        prepareStatement.setLong(6, vacancy.getDeveloper().getId());
    }

    @Override
    protected  Vacancy setEntityFields(ResultSet vacancyTableRow) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyTableRow.getLong("id"));
        vacancy.setPosition(vacancyTableRow.getString("position"));
        vacancy.setSalaryInDollarsFrom(vacancyTableRow.getFloat("salary_in_dollars_from"));
        vacancy.setSalaryInDollarsTo(vacancyTableRow.getFloat("salary_in_dollars_to"));
        vacancy.setVacancyState(VacancyState.valueOf(vacancyTableRow.getString("vacancy_state")));
        vacancy.setExperienceYearsRequire(vacancyTableRow.getFloat("experience_years_require"));
        UserDao userDao=new UserDao(connection);
        vacancy.setDeveloper(userDao.getEntityById(vacancyTableRow.getLong("id_developer")));
        return vacancy;
    }

    @Override
    protected void setValuesForDeleteIntoPrepareStatement(PreparedStatement prepareStatement, 
                                                          Vacancy entity) throws SQLException {
        prepareStatement.setLong(1, entity.getId());
    }
}
