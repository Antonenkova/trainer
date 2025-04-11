package ru.antonenkova.domain.model;

import java.util.Objects;

public class OpenQuestionCard {
    private final Long id;
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard() {
        this.id = null;
        this.question = null;
        this.expectedAnswer = null;
    }

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        if (Objects.isNull(id) || id <= 0) {
            throw new IllegalArgumentException("id должен быть натуральным");
        }
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("question не может быть пустым");
        }
        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("expectedAnswer не может быть пустым");
        }

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

    public boolean checkAnswer(String userAnswer) {
        return expectedAnswer.equalsIgnoreCase(userAnswer);
    }
}
