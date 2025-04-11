package ru.antonenkova.gui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.antonenkova.gui.controller.MainController;

import javax.swing.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainController());
    }
}
