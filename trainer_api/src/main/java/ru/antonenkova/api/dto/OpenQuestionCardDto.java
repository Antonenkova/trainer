package ru.antonenkova.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Карточка")
public class OpenQuestionCardDto {
    @Schema(description = "Номер")
    private Long id;

    @Schema(description = "Вопрос")
    private String question;

    @Schema(description = "Ответ")
    private String expectedAnswer;

    public OpenQuestionCardDto(Long id, String question, String expectedAnswer) {
        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}