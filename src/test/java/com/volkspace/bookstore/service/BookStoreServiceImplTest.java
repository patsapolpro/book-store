package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.service.impl.BookStoreServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookStoreServiceImplTest {

    @Autowired
    private BookStoreServiceImpl bookStoreService;

    @Test
    public void getBookAll(){
        List<Book> bookList = bookStoreService.getBooksAll();
        Assert.assertThat(bookList.size(), Matchers.equalTo(12));
    }
}
