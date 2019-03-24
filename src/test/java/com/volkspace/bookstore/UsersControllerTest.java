package com.volkspace.bookstore;

import com.google.gson.Gson;
import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.BookStoreService;
import com.volkspace.bookstore.service.UsersService;
import com.volkspace.bookstore.sync.BookStoreSynchronizer;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookStoreSynchronizer bookStoreSynchronizer;

    @Autowired
    private UsersService usersService;

    @Autowired
    @Qualifier("databaseBookStoreService")
    private BookStoreService bookStoreService;

    @Before
    public void setUp() throws Exception {
        bookStoreSynchronizer.forceDelete();
        bookStoreSynchronizer.forceSync();

        MultiValueMap<String, String> multiValueMapUser = new LinkedMultiValueMap<>();
        multiValueMapUser.add("username", "john.doe");
        multiValueMapUser.add("password", "thisismysecret");
        multiValueMapUser.add("date_of_birth", "15/01/1985");
        mvc.perform(delete("/users"))
                .andDo(
                    result ->  mvc.perform(post("/users").params(multiValueMapUser))
                );
    }

    @Test
    public void testCreateUsersWithRestApi() throws Exception {
        MultiValueMap<String, String> multiValueMapUser = new LinkedMultiValueMap<>();
        multiValueMapUser.add("username", "john.doe2");
        multiValueMapUser.add("password", "thisismysecret2");
        multiValueMapUser.add("date_of_birth", "15/01/1985");
        mvc.perform(post("/users").params(multiValueMapUser))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUsersWithRestApi() throws Exception {
        mvc.perform(delete("/users"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetUsersWithRestApi() throws Exception {
        Map<String, Object> sessionAttrs = new HashMap<>();
        sessionAttrs.put("userlogin", 1);

        MultiValueMap<String, String> multiValueMapLogin = new LinkedMultiValueMap<>();
        multiValueMapLogin.add("username", "john.doe");
        multiValueMapLogin.add("password", "thisismysecret");
        mvc.perform(post("/login").params(multiValueMapLogin))
                .andExpect(status().isOk())
                .andDo(
                    result ->   mvc.perform(get("/users").sessionAttrs(sessionAttrs))
                                        .andExpect(status().isOk())
                );
    }

    @Test
    public void testOrdersByUserWithRestApi() throws Exception {
        List<Users> usersList = usersService.findAll();
        Map<String, Object> sessionAttrs = new HashMap<>();
        sessionAttrs.put("userlogin", usersList.get(0).getId());

        Map<String, Double> map = new HashMap<>();
        map.put("price", 835d);

        List<Book> bookList = bookStoreService.getBooksAll();
        MultiValueMap<String, String> multiValueMapOrders = new LinkedMultiValueMap<>();
        multiValueMapOrders.add("orders", String.valueOf(bookList.get(0).getId()));
        multiValueMapOrders.add("orders", String.valueOf(bookList.get(3).getId()));

        MultiValueMap<String, String> multiValueMapLogin = new LinkedMultiValueMap<>();
        multiValueMapLogin.add("username", "john.doe");
        multiValueMapLogin.add("password", "thisismysecret");
        mvc.perform(post("/login").params(multiValueMapLogin))
                .andExpect(status().isOk())
                .andDo(
                        result ->  mvc.perform(post("/users/orders").params(multiValueMapOrders).sessionAttrs(sessionAttrs))
                                        .andExpect(status().isOk())
                                        .andExpect(content().string(Matchers.equalTo(new Gson().toJson(map))))
                );
    }
}
