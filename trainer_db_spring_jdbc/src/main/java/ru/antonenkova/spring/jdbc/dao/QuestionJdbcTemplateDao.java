package ru.antonenkova.spring.jdbc.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class QuestionJdbcTemplateDao implements QuestionRepository {
    private static final String tableName = "Tasks";
    private static final String idColumnName = "id";
    private static final String questionColumnName = "question";
    private static final String expectedAnswerColumnName = "expected_answer";

    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcTemplateDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableIfNotExistsQuery = String.format("""
            CREATE TABLE IF NOT EXISTS %s (
                %s bigint PRIMARY KEY,
                %s text,
                %s text
            );""", tableName, idColumnName, questionColumnName, expectedAnswerColumnName);

        jdbcTemplate.execute(createTableIfNotExistsQuery);
    }

    private final RowMapper<OpenQuestionCard> rowMapper = (rs, rowNum) ->
            new OpenQuestionCard(
                    rs.getLong(idColumnName),
                    rs.getString(questionColumnName),
                    rs.getString(expectedAnswerColumnName)
            );

    @Override
    public List<OpenQuestionCard> findAll() {
        String findAllSqlQuery = String.format("SELECT * FROM %s;", tableName);
        return jdbcTemplate.query(findAllSqlQuery, rowMapper);
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        String findByIdSqlQuery = String.format("SELECT * FROM %s WHERE %s = ?;", tableName, idColumnName);
        return jdbcTemplate.query(findByIdSqlQuery, rowMapper, id).stream().findFirst();
    }

    @Override
    public void add(OpenQuestionCard task) {
        String insertSqlQuery = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);",
                tableName, idColumnName, questionColumnName, expectedAnswerColumnName);
        jdbcTemplate.update(insertSqlQuery, task.getId(), task.getQuestion(), task.getExpectedAnswer());
    }

    @Override
    public void update(OpenQuestionCard task) {
        String updateSqlQuery = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;",
                tableName, questionColumnName, expectedAnswerColumnName, idColumnName);
        jdbcTemplate.update(updateSqlQuery, task.getQuestion(), task.getExpectedAnswer(), task.getId());
    }

    @Override
    public void remove(Long id) {
        String deleteSqlQuery = String.format("DELETE FROM %s WHERE %s = ?;", tableName, idColumnName);
        jdbcTemplate.update(deleteSqlQuery, id);
    }
}
