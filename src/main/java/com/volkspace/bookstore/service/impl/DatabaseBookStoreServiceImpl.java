package com.volkspace.bookstore.service.impl;

import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.repository.BookStoreRepository;
import com.volkspace.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("databaseBookStoreService")
public class DatabaseBookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Override
    public List<Book> getBooksAll() {
        return bookStoreRepository.findAll();
    }

    @Override
    public Book findById(Integer id) {
        Optional<Book> optionalBook = bookStoreRepository.findById(id);
        return optionalBook.isPresent() ? optionalBook.get() : null;

    }
}
