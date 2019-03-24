package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.impl.UsersServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersSerivceImplTest {

    @Autowired
    private UsersServiceImpl usersService;

    @Before
    public void setUp() throws Exception {
        Users users = new Users();
        users.setId(1);
        users.setUsername("john.doe");
        users.setPassword("thisismysecret");
        users.setName("john");
        users.setSurname("doe");
        users.setDateOfBirthDay(new Date());
        usersService.save(users);
    }

    @Test
    public void testSaveUsers() {
        Users users = new Users();
        users.setId(2);
        users.setUsername("john2.doe2");
        users.setPassword("thisismysecret2");
        users.setName("john2");
        users.setSurname("doe2");
        users.setDateOfBirthDay(new Date());
        usersService.save(users);

        List<Users> usersList = usersService.findAll();
        Assert.assertThat(usersList.size(), Matchers.equalTo(2));
    }

    @Test
    public void testFindByUsernameAndPassword() {
        Users usersExist = usersService.findByUsernameAndPassword("john.doe", "thisismysecret");
        Assert.assertThat(usersExist.getUsername(), Matchers.equalTo("john.doe"));
        Assert.assertThat(usersExist.getName(), Matchers.equalTo("john"));
        Assert.assertThat(usersExist.getSurname(), Matchers.equalTo("doe"));
    }

    @Test
    public void testDeleteAll() {
        usersService.deleteAll();

        List<Users> usersList = usersService.findAll();
        Assert.assertThat(usersList.size(), Matchers.equalTo(0));
    }

    @Test
    public void testFindById() {
        List<Users> usersList = usersService.findAll();

        Users usersExist = usersService.findById(usersList.get(0).getId());
        Assert.assertThat(usersExist.getUsername(), Matchers.equalTo("john.doe"));
        Assert.assertThat(usersExist.getName(), Matchers.equalTo("john"));
        Assert.assertThat(usersExist.getSurname(), Matchers.equalTo("doe"));
    }
}
