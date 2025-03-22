package ru.antonenkova.controller;

import org.springframework.stereotype.Controller;
import ru.antonenkova.domain.model.OpenQuestionCard;
import ru.antonenkova.domain.service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

@Controller
public class ConsoleController {
    private final QuestionService service;

    public ConsoleController(QuestionService service) {
        this.service = service;
    }

    public void interactWithUser() {
        long id;
        String question, expectedAnswer;
        OpenQuestionCard card;

        printHelp();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            boolean exit = false;
            System.out.print("> ");

            String[] args = scanner.nextLine().split(" ");
            switch (args[0]) {
                case "create":
                case "update":
                    id = Long.parseLong(args[1]);
                    question = args[2];
                    expectedAnswer = args[3];

                    card = new OpenQuestionCard(id, question, expectedAnswer);
                    service.save(card);

                    break;
                case "get":
                    id = Long.parseLong(args[1]);

                    Optional<OpenQuestionCard> optionalCard = service.getById(id);
                    if (optionalCard.isPresent()) {
                        card = optionalCard.get();
                        System.out.println(card.getQuestion());
                    }

                    break;
                case "list":
                    service.getAll().forEach(c -> System.out.println(c.getQuestion()));
                    break;
                case "delete":
                    id = Long.parseLong(args[1]);
                    service.delete(id);
                    break;
                case "exit":
                    exit = true;
                    break;
                case "help":
                default:
                    printHelp();
                    break;
            }

            if (exit) {
                break;
            }
        }

        scanner.close();
    }

    private void printHelp() {
        System.out.println("Список доступных команд:");
        System.out.println("create <id: Long> <question: String> <expectedAnswer: String>");
        System.out.println("get <id: Long>");
        System.out.println("list");
        System.out.println("update <id: Long> <question: String> <expectedAnswer: String>");
        System.out.println("delete <id: Long>");
        System.out.println("help");
        System.out.println("exit");
    }
}