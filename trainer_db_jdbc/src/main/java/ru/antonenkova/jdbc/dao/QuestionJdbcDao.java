package ru.antonenkova.jdbc.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {
    private static final String tableName = "Tasks";
    private static final String idColumnName = "id";
    private static final String questionColumnName = "question";
    private static final String expectedAnswerColumnName = "expected_answer";

    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String createTableIfNotExistsQuery = String.format("""
            CREATE TABLE IF NOT EXISTS %s (
                %s bigint PRIMARY KEY,
                %s text,
                %s text
            );""", tableName, idColumnName, questionColumnName, expectedAnswerColumnName);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableIfNotExistsQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        String findAllSqlQuery = String.format("SELECT * FROM %s;", tableName);

        List<OpenQuestionCard> tasks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllSqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(new OpenQuestionCard(
                        resultSet.getLong(idColumnName),
                        resultSet.getString(questionColumnName),
                        resultSet.getString(expectedAnswerColumnName)
                ));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        String findByIdSqlQuery = String.format("SELECT * FROM %s WHERE %s = ?;", tableName, idColumnName);

        OpenQuestionCard task = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByIdSqlQuery)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new OpenQuestionCard(
                        resultSet.getLong(idColumnName),
                        resultSet.getString(questionColumnName),
                        resultSet.getString(expectedAnswerColumnName)
                );
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Objects.isNull(task) ? Optional.empty() : Optional.of(task);
    }

    @Override
    public void add(OpenQuestionCard task) {
        String insertSqlQuery = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);",
                tableName, idColumnName, questionColumnName, expectedAnswerColumnName);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSqlQuery)) {
            statement.setLong(1, task.getId());
            statement.setString(2, task.getQuestion());
            statement.setString(3, task.getExpectedAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpenQuestionCard task) {
        String updateSqlQuery = String.format(
                "UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;",
                tableName, questionColumnName, expectedAnswerColumnName, idColumnName);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSqlQuery)) {
            statement.setString(1, task.getQuestion());
            statement.setString(2, task.getExpectedAnswer());
            statement.setLong(3, task.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        String deleteSqlQuery = String.format("DELETE FROM %s WHERE %s = ?;", tableName, idColumnName);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSqlQuery)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
