package com.volkspace.bookstore.controller;

import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.model.Books;
import com.volkspace.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookStoreController {

    @Autowired
    @Qualifier("databaseBookStoreService")
    private BookStoreService bookStoreService;

    @GetMapping("/books")
    public Books getBooks() {
        Books books = new Books();
        List<Book> bookList = bookStoreService.getBooksAll();
        books.setBook(bookList);
        return books;
    }
}
