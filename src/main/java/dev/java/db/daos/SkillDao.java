package dev.java.db.daos;

import dev.java.db.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDao extends AbstractDao<Skill> {
    private final static String SQL_INSERT =
            "INSERT INTO skill " +
                    "(name) " +
                    "VALUES (?)";

    private final static String SQL_UPDATE =
            "UPDATE skill " +
                    "SET name=? " +
                    "WHERE name=?";

    private final static String SQL_SELECT_BY_NAME = "SELECT * FROM skill AS s WHERE s.name=?";

    private final static String SQL_SELECT_ALL = "SELECT * FROM skill";

    public SkillDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Skill> getAllEntities() throws SQLException {
        List<Skill> allSkillsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet candidateTableRow = statement.executeQuery(SQL_SELECT_ALL);
            while (candidateTableRow.next()) {
                Skill skill = new Skill();
                setSkillFields(candidateTableRow, skill);
                allSkillsList.add(skill);
            }
            candidateTableRow.close();
        }
        return allSkillsList;
    }

    @Override
    public boolean createEntity(Skill entity) throws SQLException {
        try (PreparedStatement insertPrepareStatement = connection.prepareStatement(SQL_INSERT)) {
            setValuesForInsertIntoPrepareStatement(insertPrepareStatement, entity);
            int status =  insertPrepareStatement.executeUpdate();
            return status > 0;
        }
    }

    @Override
    public boolean updateEntity(Skill entity) throws SQLException {
        try (PreparedStatement updatePrepareStatement = connection.prepareStatement(SQL_UPDATE)) {
            setValuesForUpdateIntoPrepareStatement(updatePrepareStatement, entity);
            return updatePrepareStatement.executeUpdate() > 0;
        }
    }

    public Skill getEntityByName(String name) throws SQLException {
        try (PreparedStatement getByIdPrepareStatement = connection.prepareStatement(SQL_SELECT_BY_NAME)) {
            getByIdPrepareStatement.setString(1, name);
            ResultSet entity = getByIdPrepareStatement.executeQuery();
            if (entity.next()) {
                Skill skill = new Skill();
                setSkillFields(entity, skill);
                entity.close();
                return skill;
            }
            return null;
        }
    }

    private void setValuesForInsertIntoPrepareStatement(PreparedStatement prepareStatement, Skill skill)
            throws SQLException {
        prepareStatement.setString(1, skill.getName());
    }

    private void setValuesForUpdateIntoPrepareStatement(PreparedStatement prepareStatement, Skill skill)
            throws SQLException {
        setValuesForInsertIntoPrepareStatement(prepareStatement, skill);
        prepareStatement.setString(2, skill.getName());

    }

    private void setSkillFields(ResultSet candidateTableRow, Skill skill) throws SQLException {
        skill.setName(candidateTableRow.getString("name"));
    }
}
