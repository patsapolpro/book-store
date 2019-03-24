package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.repository.UsersRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
        Users users = new Users();
        users.setUsername("john.doe");
        users.setPassword("thisismysecret");
        users.setName("john");
        users.setSurname("doe");
        users.setDateOfBirthDay(new Date());
        usersRepository.save(users);
    }

    @Test
    public void testSaveUsers() {
        Users users = new Users();
        users.setUsername("john2.doe2");
        users.setPassword("thisismysecret2");
        users.setName("john2");
        users.setSurname("doe2");
        users.setDateOfBirthDay(new Date());
        usersRepository.save(users);
    }

    @Test
    public void testFindByUsernameAndPassword() {
        Users usersExist = usersRepository.findByUsernameAndPassword("john.doe", "thisismysecret");
        Assert.assertThat(usersExist.getUsername(), Matchers.equalTo("john.doe"));
    }

    @Test
    public void testDeleteAll() {
        usersRepository.deleteAll();
    }
}
