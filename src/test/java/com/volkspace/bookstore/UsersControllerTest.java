package com.volkspace.bookstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateUser() throws Exception {
        MultiValueMap<String, String> multiValueMapUser = new LinkedMultiValueMap<>();
        multiValueMapUser.add("username", "john.doe");
        multiValueMapUser.add("password", "thisismysecret");
        multiValueMapUser.add("date_of_birth", "15/01/1985");
        mvc.perform(post("/users").params(multiValueMapUser))
                .andExpect(status().isOk());
    }
}
