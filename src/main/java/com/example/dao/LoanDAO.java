package com.example.dao;


import main.java.com.example.model.Loan;
import main.java.com.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private Connection connection;

    // Constructeur qui initialise la connexion
    public LoanDAO(Connection connection) {
        try {
            this.connection = DatabaseUtil.getConnection(); // Initialisation de la connexion
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode d'instance utilisant la connexion
    public void someMethod() {
        // Utiliser this.connection ici
        if (connection != null) {
            // Logique pour interagir avec la base de données
        }
    }

    public void addLoan(Loan loan) {
        String query = "INSERT INTO loans (user_id, book_id, loan_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, loan.getUserId());
            statement.setInt(2, loan.getBookId());
            statement.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            if (loan.getReturnDate() != null) {
                statement.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            } else {
                statement.setDate(4, null);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLoan(Loan loan) {
        String query = "UPDATE loans SET return_date = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (loan.getReturnDate() != null) {
                statement.setDate(1, new java.sql.Date(loan.getReturnDate().getTime()));
            } else {
                statement.setDate(1, null);
            }
            statement.setInt(2, loan.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Loan getLoanByUserIdAndBookId(int userId, int bookId) {
        String query = "SELECT * FROM loans WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Loan(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("loan_date"),
                        resultSet.getDate("return_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loans";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                loans.add(new Loan(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getDate("loan_date"),
                        resultSet.getDate("return_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }
}

