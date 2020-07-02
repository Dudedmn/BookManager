package com.apis.bookmanager.controllers;

import com.apis.bookmanager.interfaces.BookDao;
import com.apis.bookmanager.models.Book;
import com.apis.bookmanager.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanager")
public class BookController {

    @Autowired
    private BookService bookService;

    private final BookDao bDao;

    public BookController(BookDao bDao){
        this.bDao = bDao;
    }

    /**
     * Gets all Books currently in the Book Collection
     * @return
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bDao.getAllBooks();
    }

    /**
     * @feature - View Books that are checked out
     * @return - list of Books checked out
     */
    @GetMapping("/checkedOut")
    public List<Book> getAllCheckedOutBooks() {
        return bookService.viewCheckedOutBooks();
    }

    /**
     * @feature - View Books that are in stock
     * @return - list of Books in stock
     */
    @GetMapping("/inStock")
    public List<Book> getAllBooksInStock() {
        return bookService.viewBooksInCollection();
    }

    /**
     * Find the first occurrence of a Book with a isbn
     * @param isbn - isbn of Book to find
     * @return - first occurrence of Book found with isbn
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> findBookByIsbn(@PathVariable int isbn) {
        try {
            Book bResult = bDao.findBookByIsbn(isbn);
            return (bResult == null) ?
                    new ResponseEntity(null, HttpStatus.NOT_FOUND)
                    :
                    ResponseEntity.ok(bResult);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Get all Books with a particular isbn
     * @param isbn - isbn of Books to get
     * @return Result of gotten Books
     */
    @GetMapping("/allBooks/{isbn}")
    public ResponseEntity<List<Book>> findAllBooksByIsbn(@PathVariable int isbn) {
        try {
            List<Book> bResult = bDao.findAllBooksByIsbn(isbn);
            return (bResult == null) ?
                    new ResponseEntity(null, HttpStatus.NOT_FOUND)
                    :
                    ResponseEntity.ok(bResult);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Update a single Book with a given isbn
     * @param isbn - isbn of Book to update
     * @param book - Book object to update to
     * @return - HTTP Response of command
     */
    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@PathVariable int isbn, @RequestBody Book book) {
        try {
            ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
            if(bDao.updateBook(isbn, book)) {
                response = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return response;
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * @feature Allows Book to be checked out or in
     * @param isbn - isbn of Book to check out/in
     * @param status - true/false if Book is to be checked out/in
     * @return - HTTP OK if successful, NOT_FOUND if unsuccessful
     */
    @PutMapping(value = "/{isbn}", params = "status")
    public ResponseEntity updateBookStatus(@PathVariable int isbn,
                                            @RequestParam("status") boolean status) {
        try {
            if(bookService.findBookByISBN(isbn) != null) {
                bookService.findBookByISBN(isbn).setCheckedOut(status);
                return new ResponseEntity((HttpStatus.OK));
            }
            return new ResponseEntity((HttpStatus.NOT_FOUND));
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Update a Book with parameter values from a found isbn
     * @param isbn - isbn of Book to update
     * @param newIsbn - new isbn for updated Book
     * @param title - new title for updated Book
     * @param author - new author for updated Book
     * @return - HTTP Response of command
     */
    @PutMapping(value = "/{isbn}", params = {"isbn", "title", "author"})
    public ResponseEntity updateBook(
                                     @PathVariable int isbn,
                                     @RequestParam("isbn") int newIsbn,
                                     @RequestParam("title") String title,
                                     @RequestParam("author") String author) {
        try {
            ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
            if(bDao.updateBook(isbn, newIsbn, title, author)) {
                response = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return response;
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Update all Books with the same entered isbn using a Book object
     * @param isbn - isbn of Books to find
     * @param book - new Book parameters to update to
     * @return - HTTP Command status
     */
    @PutMapping("/updateAll/{isbn}")
    public ResponseEntity updateAllBooks(@PathVariable int isbn, @RequestBody Book book) {
        try {
            ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
           if(bDao.updateAllBooks(isbn, book)) {
                response = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return response;
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Attempts to update all Books with the same isbn using parameter values
     * @param isbn - isbn of Books to update
     * @param newIsbn - new isbn for updated Books
     * @param title - new title for updated Books
     * @param author - new author for updated Books
     * @return - HTTP Response of command
     */
    @PutMapping(value = "/updateAll/{isbn}", params = {"isbn", "title", "author"})
    public ResponseEntity updateAllBooks(
                                     @PathVariable int isbn,
                                     @RequestParam("isbn") int newIsbn,
                                     @RequestParam("title") String title,
                                     @RequestParam("author") String author) {
        try{
                ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
            if(bDao.updateAllBooks(isbn, newIsbn, title, author)){
                response = new ResponseEntity(HttpStatus.NO_CONTENT);
            }
                return response;
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * @feature - Adds a Book to the collection of books
     * @param book - Book to add
     * @return - added book
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        try {
            return bDao.addBook(book);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Adds a book through specific parameters
     * @param isbn - isbn of Book to add
     * @param title - title of Book to add
     * @param author - author of Book to add
     * @return - added Book
     */
    @PostMapping(params = {"isbn", "title", "author"})
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(
            @RequestParam("isbn") int isbn,
            @RequestParam("title") String title,
            @RequestParam("author") String author) {
        try{
            return bDao.addBook(isbn, title, author);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * @feature - Removes a Book from stock through isbn
     * @param isbn - isbn of the Book to be removed
     * @return - HTTP responses based on removal status
     */
    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable int isbn) {
        try{
            return (bDao.deleteBookByISBN(isbn)) ?
                    new ResponseEntity(HttpStatus.OK)
                    :
                    new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Attempts to delete all Books with the same isbn
     * @return - HTTP Response status
     */
    @DeleteMapping("/deleteAll/{isbn}")
    public ResponseEntity deleteAllBooks(@PathVariable int isbn) {
        try{
            return (bDao.deleteAllBooksByISBN(isbn)) ?
                    new ResponseEntity(HttpStatus.OK)
                    :
                    new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch (IllegalArgumentException e) {
            System.err.println("ILLEGAL_ARGUMENT_ERROR:" + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return null;
        }
    }

    /**
     * Attempts to delete all Books with the same isbn
     * @return - HTTP Response status
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll() {
        try{
            bDao.deleteAll();
        }
        catch (MethodArgumentTypeMismatchException e ) {
            System.err.println("ARGUMENT_TYPE_MISMATCH_ERROR: " + e);
        }
        catch(HttpMessageNotReadableException e) {
            System.err.println("HTTP_READABLE_ERROR: " + e);
        }
        catch(Exception e) {
            System.err.println("ERROR: " + e);
        }
        finally {
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
