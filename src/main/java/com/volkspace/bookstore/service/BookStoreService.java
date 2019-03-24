package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Book;

import java.util.List;

public interface BookStoreService {
    List<Book> getBooksAll();

    Book findById(Integer id);
}
