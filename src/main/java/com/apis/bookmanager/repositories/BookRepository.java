package com.apis.bookmanager.repositories;

import com.apis.bookmanager.interfaces.BookDao;
import com.apis.bookmanager.models.Book;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements BookDao {
    @Getter @Setter private static List<Book> books = new ArrayList<>();

    /**
     * Adds a new Book based on passed in Book object
     * @param book
     * @return - added Book object
     */
    @Override
    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    /**
     * Add a new Book based on specific parameters
     * @param isbn - new isbn for a Book
     * @param title - new title for a Book
     * @param author - new author for a Book
     * @return - added Book object
     */
    @Override
    public Book addBook(int isbn, String title, String author) {
        Book book = new Book(isbn, title, author);
        books.add(book);
        return book;
    }

    /**
     * Gets a copy of the current list of Books
     * @return Copy list of the current list of Books
     */
    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    /**
     * Attempts to find the first Book by isbn
     * @param isbn - isbn to find the Book by
     * @return - Book to return
     */
    @Override
    public Book findBookByIsbn(int isbn) {
        return books.stream()
                .filter(b->b.getIsbn() == isbn)
                .findFirst()
                .orElse(null);
    }

    /**
     * Attempts to find all duplicate Books by isbn
     * @param isbn - isbn to find Books by
     * @return - List of duplicated Books
     */
    @Override
    public List<Book> findAllBooksByIsbn(int isbn) {
        List<Book> duplicateBooks = new ArrayList<>();
        for (Book book : books){
            if(book.getIsbn() == isbn) {
                duplicateBooks.add(book);
            }
        }
        return duplicateBooks;
    }

    /**
     * Attempts to update the first Book found by an isbn
     * @param book - new Book to set the Book to
     * @return true if a Book was updated
     */
    @Override
    public boolean updateBook(int isbn, Book book) {
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getIsbn() == isbn) {
                books.get(i).setBook(book);
                // Simply update the first occurrence
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to update the first Book found by an isbn
     * @param isbn - isbn to find the Book
     * @param newIsbn - new isbn to set the Book to
     * @param title - new title to set the Book to
     * @param author - new author to set the Book to
     * @return true if a Book was updated
     */
    @Override
    public boolean updateBook(int isbn, int newIsbn, String title, String author) {
        Book bookToCheck = new Book(newIsbn, title, author);
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getIsbn() == isbn) {
                books.get(i).setBook(bookToCheck);
                // Simply update the first occurrence
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to update all Books found by an isbn
     * @param isbn - isbn to find Books
     * @param book - Book to update all found Books to
     * @return true if number of found books equals deleted counter
     */
    @Override
    public boolean updateAllBooks(int isbn, Book book) {
        ArrayList<Book> duplicateBooks = new ArrayList<>(findAllBooksByIsbn(isbn));
        int updatedCounter = 0;
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getIsbn() == isbn) {
                books.get(i).setBook(book);
                updatedCounter++;
            }
        }
        return (updatedCounter == duplicateBooks.size() ? true : false);
    }

    /**
     * Attempts to update all Books found by an isbn
     * @param isbn - isbn to find Books
     * @param newIsbn - new isbn to set all Books to
     * @param title - new title to set all Books to
     * @param author - new author to set all Books to
     * @return true if number of found Books equals updated counter
     */
    @Override
    public boolean updateAllBooks(int isbn, int newIsbn, String title, String author) {
        ArrayList<Book> duplicateBooks = new ArrayList<>(findAllBooksByIsbn(isbn));
        int updatedCounter = 0;
        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getIsbn() == isbn) {
                books.get(i).setBook(newIsbn, title, author);
                updatedCounter++;
            }
        }
        return (updatedCounter == duplicateBooks.size() ? true : false);
    }

    /**
     * Attempts to delete the first Book found by an isbn
     * @param isbn - isbn to find a Book
     * @return - true if removal was successful
     */
    @Override
    public boolean deleteBookByISBN(int isbn) {
        if(!books.contains(findBookByIsbn(isbn)))
            return false;
        else {
            books.remove(findBookByIsbn(isbn));
            return true;
        }
    }

    /**
     * Attempts to delete all Books found by an isbn
     * @param isbn - isbn to find Books
     * @return true if all Books were removed
     */
    @Override
    public boolean deleteAllBooksByISBN(int isbn) {
        if(!books.contains(findBookByIsbn(isbn)))
            return false;

        while(books.contains(findBookByIsbn(isbn))){
            books.remove(findBookByIsbn(isbn));
        }
        return true;
    }

    /**
     * Deletes all Books in the repository
     */
    @Override
    public void deleteAll() {
        books.clear();
    }


}
