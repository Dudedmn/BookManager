package com.apis.bookmanager.models;

import lombok.Getter;
import lombok.Setter;

public class Book {
    // Typical identification methods for a Book, none are unique
    @Getter @Setter private int isbn;
    @Getter @Setter private String title;
    @Getter @Setter private String author;
    // In this setting of a Book Manager each Book would be tagged with a checkedOut property
    @Getter @Setter private boolean isCheckedOut;

    /**
     * Generate a Book based on provided parameters
     * @param title - title of Book
     * @param author - author of Book
     * @param isbn - unique identifier for Book
     */
    public Book(int isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isCheckedOut = false;
    }

    /**
     * Copy constructor if book is not a duplicate
     * @param otherObject - non duplicate Book object
     */
    public Book(Object otherObject) {
        if(otherObject instanceof Book && checkDuplicateBook((Book) otherObject)) {
            Book otherBook = (Book) otherObject;
            this.isbn = otherBook.isbn;
            this.title = otherBook.title;
            this.author = otherBook.author;
            this.isCheckedOut = false;
        }
    }

    /**
     * Checks if a Book object is a duplicate of current Book
     * @param otherBook - other Book object
     * @return true/false if successful/unsuccessful
     */
    private boolean checkDuplicateBook(Book otherBook) {
        if(this.isbn != otherBook.isbn) {
            if(this.title.compareTo(otherBook.title) != 0) {
                if(this.author.compareTo(otherBook.author) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets this current Book to passed in Book object
     * @param book - Book object to be set to
     */
    public void setBook(Book book){
        this.isbn = book.isbn;
        this.title = book.title;
        this.author = book.author;
    }

    /**
     * Sets this current Book to passed in parameters
     */
    public void setBook(int isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    /**
     * Overridden equals to compare an Object to another Book
     * @param otherObject - other object to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        // Check if object is the same
        if (otherObject == this) {
            return true;
        }

        // If the object is not a Book instance return false
        if (!(otherObject instanceof Book)) {
            return false;
        }

        // Cast other object to Book
        Book compareBook = (Book) otherObject;
        // Check proper fields
        return (this.isbn == compareBook.isbn
                && this.title.compareTo(compareBook.title) == 0
                && this.author.compareTo(compareBook.author) == 0);

    }

    /**
     * Overridden toString() for easier printing
     * @return formatted String
     */
    @Override
    public String toString(){
        return String.format("  ISBN: %s%n Title: %s%n Author: %s%n", isbn, title, author);
    }

    // Methods are fussy with this getter
    public int getIsbn() {
        return isbn;
    }
}
