package com.volkspace.bookstore.service.impl;

import com.volkspace.bookstore.external.BookData;
import com.volkspace.bookstore.external.BookExternalService;
import com.volkspace.bookstore.external.BookResponse;
import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("bookStoreService")
public class BookStoreServiceImpl implements BookStoreService {

    @Autowired
    private BookExternalService bookExternalService;

    @Override
    public List<Book> getBooksAll() {
        List<Book> bookList = new ArrayList<>();
        BookResponse bookResponse = bookExternalService.fetchAll();

        Map<Integer, BookData> mapBookRecommendation = createMapBook(bookExternalService.fetchRecommendation());
        for (BookData bookData: bookResponse) {
            Book book = createBook(mapBookRecommendation, bookData);
            if (!bookList.contains(book)) {
                bookList.add(book);
            }
        }
        return bookList.stream()
                .sorted(Comparator.comparing(Book::getIs_recommended).reversed().thenComparing(Book::getName))
                .collect(Collectors.toList());
    }

    private Book createBook(Map<Integer, BookData> mapBookRecommendation, BookData bookData) {
        Book book = new Book();
        book.setId(bookData.getId());
        book.setName(bookData.getBook_name());
        book.setAuthor(bookData.getAuthor_name());
        book.setPrice(bookData.getPrice());

        if(mapBookRecommendation.containsKey(bookData.getId())){
            book.setIs_recommended(true);
        } else {
            book.setIs_recommended(false);
        }
        return book;
    }

    private Map<Integer, BookData> createMapBook(BookResponse bookData) {
        Map<Integer, BookData> mapBook = new HashMap<>();
        for (BookData book : bookData) {
            mapBook.put(book.getId(), book);
        }
        return mapBook;
    }

}
