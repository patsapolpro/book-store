package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersSerivceImplTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testSaveUsers() {
        Users users = new Users();
        users.setUsername("john.doe");
        users.setPassword("thisismysecret");
        users.setName("john");
        users.setSurname("doe");
        users.setDateOfBirthDay(new Date());
        usersRepository.save(users);
    }
}
