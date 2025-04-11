package ru.antonenkova.gui.model;

import ru.antonenkova.domain.model.OpenQuestionCard;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {
    private final String[] columns = {"Номер", "Вопрос", "Ответ"};
    private List<OpenQuestionCard> questions;

    public QuestionTableModel(List<OpenQuestionCard> questions) {
        this.questions = questions;
    }

    public void setQuestions(List<OpenQuestionCard> updated) {
        this.questions = updated;
        fireTableDataChanged();
    }

    @Override public int getRowCount() {
        return questions.size();
    }

    @Override public int getColumnCount() {
        return columns.length;
    }

    @Override public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        OpenQuestionCard q = questions.get(row);
        return switch (col) {
            case 0 -> q.getId();
            case 1 -> q.getQuestion();
            case 2 -> q.getExpectedAnswer();
            default -> "";
        };
    }
}

