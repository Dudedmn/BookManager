package com.apis.bookmanager.interfaces;

import com.apis.bookmanager.models.Book;

import java.util.List;

public interface BookDao {
    // Adds a Book based on Book object
    Book addBook(Book book);

    // Adds a Book based on passed in parameters
    Book addBook(int isbn, String title, String author);

    // Returns a List of all Books
    List<Book> getAllBooks();

    // Finds the first occurrence of a Book by ISBN
    Book findBookByIsbn(int isbn);

    // Finds all occurrences of a Book by ISBN
    List<Book> findAllBooksByIsbn(int isbn);

    // Updates a given Book object with another Book object
    boolean updateBook(int isbn, Book book);

    // Updates a given Book object with specific parameters
    boolean updateBook(int isbn, int newIsbn, String title, String author);

    // Updates all Books by isbn with a passed in Book
    boolean updateAllBooks(int isbn, Book book);

    // Updates all Books by isbn with specific parameters
    boolean updateAllBooks(int isbn, int newIsbn, String title, String author);

    // Deletes the first retrieved book by isbn
    boolean deleteBookByISBN(int isbn);

    // Deletes all found Book with the same isbn
    boolean deleteAllBooksByISBN(int isbn);

    // Delete all Book objects in the Collection
    void deleteAll();
}
