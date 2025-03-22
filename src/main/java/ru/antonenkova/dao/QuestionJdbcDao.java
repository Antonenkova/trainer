package ru.antonenkova.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {
    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<OpenQuestionCard> rowMapper = (rs, rowNum) ->
            new OpenQuestionCard(
                    rs.getLong("id"),
                    rs.getString("question"),
                    rs.getString("expected_answer")
            );

    @Override
    public List<OpenQuestionCard> findAll() {
        String findAllSqlQuery = "select * from questions";
        return jdbcTemplate.query(findAllSqlQuery, rowMapper);
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        String findByIdSqlQuery = "select * from questions where id = ?";
        return jdbcTemplate.query(findByIdSqlQuery, rowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void add(OpenQuestionCard task) {
        String insertSqlQuery = "insert into questions (id, question, expected_answer) values (?, ?, ?)";
        jdbcTemplate.update(insertSqlQuery, task.getId(), task.getQuestion(), task.getExpectedAnswer());
    }

    @Override
    public void update(OpenQuestionCard task) {
        String updateSqlQuery = "UPDATE questions SET question = ?, expected_answer = ? WHERE id = ?";
        jdbcTemplate.update(updateSqlQuery, task.getQuestion(), task.getExpectedAnswer(), task.getId());
    }

    @Override
    public void remove(Long id) {
        String deleteSqlQuery = "DELETE FROM questions WHERE id = ?";
        jdbcTemplate.update(deleteSqlQuery, id);
    }
}
