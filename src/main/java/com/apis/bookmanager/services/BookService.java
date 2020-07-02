package com.apis.bookmanager.services;

import com.apis.bookmanager.models.Book;
import com.apis.bookmanager.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This service class is just a layer on the repository class. Any methods with no comments should refer to
 * bookRepository comments as they're likely defined there.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public Book addBook(int isbn, String title, String author) {
        return bookRepository.addBook(isbn, title, author);
    }

    /**
     * Showcases a list of Books that are not checked out or are in stock
     * @return - Books in stock
     */
    public List<Book> viewBooksInCollection() {
        List<Book> inStock = new ArrayList<>();
        for(Book book : bookRepository.getAllBooks()) {
            if(!book.isCheckedOut())
                inStock.add(book);
        }
        return inStock;
    }

    /**
     * Showcases a list of Books that are checked out
     * @return - Books not in stock
     */
    public List<Book> viewCheckedOutBooks() {
        List<Book> checkedOut = new ArrayList<>();
        for(Book book : bookRepository.getAllBooks()) {
            if(book.isCheckedOut()) {
                checkedOut.add(book);
            }
        }
        return checkedOut;
    }

    /**
     * Checkout a Book by isbn
     * @param isbn - isbn to check out a book
     * @return - first Book found with isbn
     */
    public boolean checkOutBook(int isbn) {
        if(bookRepository.findBookByIsbn(isbn) != null) {
            bookRepository.findBookByIsbn(isbn).setCheckedOut(true);
            return true;
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book findBookByISBN(int isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public List<Book> findAllBooksByIsbn(int isbn) {
        return bookRepository.findAllBooksByIsbn(isbn);
    }

    public boolean updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    public boolean updateBook(int isbn, int newIsbn, String title, String author) {
        return bookRepository.updateBook(isbn, newIsbn, title, author);
    }

    public boolean updateAllBooks(int isbn, Book book) {
        return bookRepository.updateAllBooks(isbn, book);
    }

    public boolean updateAllBooks(int isbn, int newIsbn, String title, String author) {
        return bookRepository.updateAllBooks(isbn, newIsbn, title, author);
    }

    public boolean deleteBookByISBN(int isbn) {
        return bookRepository.deleteBookByISBN(isbn);
    }

    public boolean deleteAllBooksByISBN(int isbn) {
        return bookRepository.deleteAllBooksByISBN(isbn);
    }
}
