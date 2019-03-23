package com.volkspace.bookstore.service.impl;

import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.repository.BookStoreRepository;
import com.volkspace.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("databaseBookStoreService")
public class DatabaseBookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public List<Book> getBooksAll() {
        return bookStoreRepository.findAll();
    }
}
