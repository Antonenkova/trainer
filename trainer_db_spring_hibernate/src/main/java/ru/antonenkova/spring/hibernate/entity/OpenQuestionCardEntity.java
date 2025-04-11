package ru.antonenkova.spring.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class OpenQuestionCardEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "expected_answer")
    private String expectedAnswer;

    public OpenQuestionCardEntity() {}

    public OpenQuestionCardEntity(Long id, String question, String expectedAnswer) {
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
