package com.example.controller;

import com.example.dao.LoanDAO;
import main.java.com.example.model.Loan;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class LoanController {
    @FXML
    private TextField userIdField;
    @FXML
    private TextField bookIdField;
    @FXML
    private Button borrowButton;
    @FXML
    private Button returnButton;

    private Connection connection;
    private LoanDAO loanDAO;

    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
            loanDAO = new LoanDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBorrowBook() {
        int userId = Integer.parseInt(userIdField.getText());
        int bookId = Integer.parseInt(bookIdField.getText());

        Loan loan = new Loan(0, userId, bookId, new Date(), null);
        loanDAO.addLoan(loan);

        // Afficher un message de succès ou vider les champs
    }

    @FXML
    public void handleReturnBook() {
        int userId = Integer.parseInt(userIdField.getText());
        int bookId = Integer.parseInt(bookIdField.getText());

        Loan loan = loanDAO.getLoanByUserIdAndBookId(userId, bookId); // Utiliser l'instance de LoanDAO
        if (loan != null) {
            loan.setReturnDate(new Date());
            loanDAO.updateLoan(loan);
            // Afficher un message de succès ou vider les champs
        } else {
            // Afficher un message indiquant qu'aucun prêt n'a été trouvé
        }
    }

    // Ajoutez une méthode pour initialiser les champs, si nécessaire

}

