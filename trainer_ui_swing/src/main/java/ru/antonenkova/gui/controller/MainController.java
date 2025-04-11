package ru.antonenkova.gui.controller;

import org.springframework.stereotype.Controller;
import ru.antonenkova.client.QuestionClient;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.service.QuestionService;
import ru.antonenkova.gui.model.QuestionTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Controller
public class MainController implements Runnable {
    private final QuestionClient service = new QuestionClient();
    private List<OpenQuestionCard> questions;
    private QuestionTableModel model;
    private JTable table;

    @Override
    public void run() {
        questions = service.getAll();
        model = new QuestionTableModel(questions);
        table = new JTable(model);

        JFrame frame = new JFrame("Вопросы");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 400);
        frame.setLocationRelativeTo(null);

        JPanel buttons = new JPanel();
        JButton add = new JButton("Добавить");
        JButton edit = new JButton("Редактировать");
        JButton delete = new JButton("Удалить");

        add.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Введите номер вопроса:");
            if (id == null || id.isBlank()) return;

            String question = JOptionPane.showInputDialog("Введите вопрос:");
            if (question == null || question.isBlank()) return;

            String answer = JOptionPane.showInputDialog("Введите ответ:");
            if (answer == null) return;

            OpenQuestionCard newCard = new OpenQuestionCard(Long.parseLong(id), question, answer);
            service.create(newCard);
            reloadData();
        });

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Нужно выбрать строку с вопросом!");
                return;
            }

            OpenQuestionCard oldCard = questions.get(row);

            String newQuestion = JOptionPane.showInputDialog("Редактировать вопрос:", oldCard.getQuestion());
            if (newQuestion == null) return;

            String newAnswer = JOptionPane.showInputDialog("Редактировать ответ:", oldCard.getExpectedAnswer());
            if (newAnswer == null) return;

            OpenQuestionCard newCard = new OpenQuestionCard(oldCard.getId(), newQuestion, newAnswer);
            service.update(newCard);
            reloadData();
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Нужно выбрать строку с вопросом!");
                return;
            }

            OpenQuestionCard card = questions.get(row);
            service.delete(card.getId());
            reloadData();
        });

        buttons.add(add);
        buttons.add(edit);
        buttons.add(delete);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void reloadData() {
        questions = service.getAll();
        model.setQuestions(questions);
    }
}
