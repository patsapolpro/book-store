package com.volkspace.bookstore;

import com.volkspace.bookstore.service.OrdersService;
import com.volkspace.bookstore.service.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrdersService ordersService;

    @Before
    public void setUp() throws Exception {
        usersService.deleteAll();
        ordersService.deleteAll();

        MultiValueMap<String, String> multiValueMapUser = new LinkedMultiValueMap<>();
        multiValueMapUser.add("username", "john.doe");
        multiValueMapUser.add("password", "thisismysecret");
        multiValueMapUser.add("date_of_birth", "15/01/1985");
        mvc.perform(delete("/users"))
                .andDo(
                    result -> mvc.perform(post("/users").params(multiValueMapUser))
                );
    }

    @Test
    public void loginWithRestApiWithExistUsers() throws Exception {
        MultiValueMap<String, String> multiValueMapLogin = new LinkedMultiValueMap<>();
        multiValueMapLogin.add("username", "john.doe");
        multiValueMapLogin.add("password", "thisismysecret");
        mvc.perform(post("/login").params(multiValueMapLogin))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithRestApiWithOutExistUsers() throws Exception {
        MultiValueMap<String, String> multiValueMapLogin = new LinkedMultiValueMap<>();
        multiValueMapLogin.add("username", "john.doe");
        multiValueMapLogin.add("password", "thisismysecret2");
        mvc.perform(post("/login").params(multiValueMapLogin))
                .andExpect(status().isNotFound());
    }

    @Test
    public void loginWithRestApiWithOutExistUsersButInvalidPassword() throws Exception {
        MultiValueMap<String, String> multiValueMapLogin = new LinkedMultiValueMap<>();
        multiValueMapLogin.add("username", "john.doe");
        multiValueMapLogin.add("password", "thisismysecret2");
        mvc.perform(post("/login").params(multiValueMapLogin))
                .andExpect(status().isNotFound());
    }
}
