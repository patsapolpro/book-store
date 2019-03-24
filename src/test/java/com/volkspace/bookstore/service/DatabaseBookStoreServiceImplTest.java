package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.service.impl.DatabaseBookStoreServiceImpl;
import com.volkspace.bookstore.sync.BookStoreDatabaseInitializer;
import com.volkspace.bookstore.sync.BookStoreSynchronizer;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseBookStoreServiceImplTest {

    @Autowired
    private DatabaseBookStoreServiceImpl databaseBookStoreService;

    @Autowired
    private BookStoreSynchronizer bookStoreSynchronizer;

    @Before
    public void setUp() throws Exception {
        bookStoreSynchronizer.forceDelete();
        bookStoreSynchronizer.forceSync();
    }

    @Test
    public void getBookAll() {
        List<Book> bookList = databaseBookStoreService.getBooksAll();
        Assert.assertThat(bookList.size(), Matchers.equalTo(12));
    }
}