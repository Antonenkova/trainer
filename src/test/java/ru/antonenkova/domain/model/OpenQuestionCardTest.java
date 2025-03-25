package ru.antonenkova.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class OpenQuestionCardTest {
    private OpenQuestionCard card;

    @BeforeEach
    void setUp() {
        card = new OpenQuestionCard(1L, "Как зовут кота Шредингера?", "Барсик");
    }

    @Test
    @DisplayName("Создание карточки с валидными данными")
    void shouldCreateCardSuccessfully() {
        assertEquals("Как зовут кота Шредингера?", card.getQuestion());
    }

    @Test
    @DisplayName("Исключение при невалидном идентификаторе")
    void shouldThrowExceptionWhenIdIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(0L, "Вопрос", "Ответ"));
        assertEquals("id должен быть натуральным", exception.getMessage());
    }

    @Test
    @DisplayName("Исключение при пустом вопросе")
    void shouldThrowExceptionWhenQuestionIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L, "", "Ответ"));
        assertEquals("question не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Исключение при пустом ответе")
    void shouldThrowExceptionWhenAnswerIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard(1L, "Вопрос", ""));
        assertEquals("expectedAnswer не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка правильного ответа без учета регистра")
    void shouldCheckCorrectAnswerIgnoringCase() {
        OpenQuestionCard anotherCard = new OpenQuestionCard(1L, "Сколько ног у паука?", "Восемь");
        assertTrue(anotherCard.checkAnswer("вОсемь"));
    }

    @Test
    @DisplayName("Проверка неверного ответа")
    void shouldReturnFalseForIncorrectAnswer() {
        OpenQuestionCard anotherCard = new OpenQuestionCard(1L, "Какой сегодня день?", "Понедельник");
        assertFalse(anotherCard.checkAnswer("Суббота"));
    }
}
