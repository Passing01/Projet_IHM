package com.example.controller;


import main.java.com.example.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label welcomeLabel;

    private User user;

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Bienvenue, " + user.getUsername());
    }
}

