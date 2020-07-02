package com.apis.bookmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author - Daniel Yan
 * @version - 2020/07/02
 * Description: Simple Spring MvC Book Manager Application
 *
 * Primary features are tagged with @feature
 *
 * @feature - Add books to their collection
 * @feature - View a list of books in stock
 * @feature - View a list of books currently checked out
 * @feature - Check out a book
 * @feature - Remove a book from stock
 *
 */
@SpringBootApplication
public class BookManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagerApplication.class, args);
    }
}