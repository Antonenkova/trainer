package ru.antonenkova.domain.model;

import java.util.Objects;

public class OpenQuestionCard {
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(String question, String expectedAnswer) {
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("question не может быть пустым");
        }
        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("expectedAnswer не может быть пустым");
        }
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }


    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String userAnswer) {
        return expectedAnswer.equalsIgnoreCase(userAnswer);
    }
}
