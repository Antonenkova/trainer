package ru.antonenkova.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.antonenkova.api.dto.OpenQuestionCardDto;
import ru.antonenkova.api.mapper.QuestionDtoMapper;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.repo.QuestionRepository;
import ru.antonenkova.domain.service.QuestionService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionControllerTests {
    private final String Question = "Question";
    private final String Answer = "Answer";

    private QuestionRepository repo;
    private QuestionDtoMapper mapper;
    private QuestionController controller;

    @BeforeEach
    void setUp() {
        repo = mock(QuestionRepository.class);
        mapper = mock(QuestionDtoMapper.class);
        controller = new QuestionController(new QuestionService(repo), mapper);
    }

    @Test
    void getById_shouldReturnQuestion() {
        var model = new OpenQuestionCard(1L, Question, Answer);
        var dto = new OpenQuestionCardDto(1L, Question, Answer);

        when(repo.findById(1L)).thenReturn(Optional.of(model));
        when(mapper.toDto(model)).thenReturn(dto);

        Optional<OpenQuestionCardDto> result = controller.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(Question, result.get().getQuestion());
    }

    @Test
    void getById_noQuestion_shouldReturnEmptyOptional() {
        var model = new OpenQuestionCard(1L, Question, Answer);
        var dto = new OpenQuestionCardDto(1L, Question, Answer);

        when(repo.findById(1L)).thenReturn(Optional.empty());
        when(mapper.toDto(model)).thenReturn(dto);

        Optional<OpenQuestionCardDto> result = controller.getById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAll_shouldReturnAllQuestions() {
        var model1 = new OpenQuestionCard(1L, Question, Answer);
        var dto1 = new OpenQuestionCardDto(1L, Question, Answer);
        var model2 = new OpenQuestionCard(2L, Question, Answer);
        var dto2 = new OpenQuestionCardDto(2L, Question, Answer);

        when(repo.findAll()).thenReturn(List.of(model1, model2));
        when(mapper.toDto(model1)).thenReturn(dto1);
        when(mapper.toDto(model2)).thenReturn(dto2);

        List<OpenQuestionCardDto> result = controller.getAll();

        assertEquals(2, result.size());
        assertEquals(Question, result.get(0).getQuestion());
        assertEquals(Question, result.get(1).getQuestion());
    }

    @Test
    void create_shouldCreateQuestion() {
        var model = new OpenQuestionCard(1L, Question, Answer);
        var dto = new OpenQuestionCardDto(2L, Question, Answer);

        when(mapper.toModel(dto)).thenReturn(model);

        controller.create(dto);

        verify(repo).add(model);
    }

    @Test
    void update_shouldUpdateQuestion() {
        var model = new OpenQuestionCard(1L, Question, Answer);
        var dto = new OpenQuestionCardDto(1L, Question, Answer);

        when(mapper.toModel(dto)).thenReturn(model);
        when(repo.findById(1L)).thenReturn(Optional.of(model));

        controller.update(dto);

        verify(repo).update(model);
    }

    @Test
    void delete_shouldDeleteQuestion() {
        controller.delete(42L);
        verify(repo).remove(42L);
    }
}
