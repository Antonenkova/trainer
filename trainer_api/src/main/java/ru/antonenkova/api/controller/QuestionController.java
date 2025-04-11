package ru.antonenkova.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.antonenkova.api.dto.OpenQuestionCardDto;
import ru.antonenkova.api.mapper.QuestionDtoMapper;
import ru.antonenkova.domain.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionDtoMapper mapper;

    public QuestionController(QuestionService questionService, QuestionDtoMapper mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }

    @Operation(summary = "Получить карточку по номеру")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<OpenQuestionCardDto> getById(@Parameter(description = "Номер") @PathVariable Long id) {
        return questionService.getById(id).map(mapper::toDto);
    }

    @Operation(summary = "Получить все карточки")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OpenQuestionCardDto> getAll() {
        return questionService.getAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Создать карточку")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void create(@Parameter(description = "Новая карточка") @RequestBody OpenQuestionCardDto dto) {
        questionService.save(mapper.toModel(dto));
    }

    @Operation(summary = "Редактировать карточку")
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@Parameter(description = "Существующая карточка") @RequestBody OpenQuestionCardDto dto) {
        questionService.save(mapper.toModel(dto));
    }

    @Operation(summary = "Удалить вопрос")
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "Номер карточки") @PathVariable Long id) {
        questionService.delete(id);
    }
}