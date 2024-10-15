package com.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.dao.BookDAO;
import main.java.com.example.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;  // Correcte pour JavaFX

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class BookController {
    @FXML
    private ListView<Book> bookListView;  // Assurez-vous que la variable est bien liée via FXML
    @FXML
    private TextField titleField;
    @FXML
    private TextField searchField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearField;
    @FXML
    private Button addBookButton;

    private Connection connection;
    private BookDAO bookDAO;




    @FXML
    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
            bookDAO = new BookDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        int year = Integer.parseInt(yearField.getText());

        Book book = new Book(0, title, author, year, true);
        bookDAO.addBook(book);

        // Afficher un message de succès ou vider les champs
    }

    @FXML
    public void handleDeleteBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookDAO.deleteBook(selectedBook.getId());
            bookListView.getItems().remove(selectedBook);
        }

        // Afficher un message de succès ou vider les champs
    }

    @FXML
    public void handleSearchBooks() {
        String query = searchField.getText();
        List<Book> books = bookDAO.searchBooks(query);
        ObservableList<Book> observableList = FXCollections.observableArrayList(books);
        bookListView.setItems(observableList);
    }
}

