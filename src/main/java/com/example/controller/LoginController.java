package com.example.controller;


import com.example.dao.UserDAO;
import main.java.com.example.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private Connection connection;
    private UserDAO userDAO;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
            System.out.println("Connection established successfully.");
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }


    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (userDAO != null) {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/Dashboard.fxml"));
                    Parent root = loader.load();
                    com.example.controller.DashboardController controller = loader.getController();
                    controller.setUser(user);

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Afficher un message d'erreur pour des identifiants incorrects
                System.out.println("Identifiants incorrects");
            }
        } else {
            System.out.println("userDAO is null");
        }
    }


    @FXML
    public void handleRegisterRedirect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/view/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

