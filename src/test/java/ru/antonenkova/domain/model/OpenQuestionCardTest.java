package ru.antonenkova.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class OpenQuestionCardTest {

    private OpenQuestionCard card;

    @BeforeEach
    void setUp() {
        card = new OpenQuestionCard("Как зовут кота Шредингера?", "Барсик");
    }

    @Test
    @DisplayName("Создание карточки с валидными данными")
    void shouldCreateCardSuccessfully() {
        assertEquals("Как зовут кота Шредингера?", card.getQuestion());
    }

    @Test
    @DisplayName("Исключение при пустом вопросе")
    void shouldThrowExceptionWhenQuestionIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard("", "Ответ"));
        assertEquals("question не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Исключение при пустом ответе")
    void shouldThrowExceptionWhenAnswerIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new OpenQuestionCard("Вопрос", ""));
        assertEquals("expectedAnswer не может быть пустым", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка правильного ответа без учета регистра")
    void shouldCheckCorrectAnswerIgnoringCase() {
        OpenQuestionCard anotherCard = new OpenQuestionCard("Сколько ног у паука?", "Восемь");
        assertTrue(anotherCard.checkAnswer("вОсемь"));
    }

    @Test
    @DisplayName("Проверка неверного ответа")
    void shouldReturnFalseForIncorrectAnswer() {
        OpenQuestionCard anotherCard = new OpenQuestionCard("Какой сегодня день?", "Понедельник");
        assertFalse(anotherCard.checkAnswer("Суббота"));
    }
}
