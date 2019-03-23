package com.volkspace.bookstore.controller;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.UsersService;
import com.volkspace.bookstore.util.BookStoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/users")
    public String createUsers(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam("date_of_birth") String dateOfBirth) {
        String result;
        try {
            String[] nameSplit = username.split(".");

            Users users = new Users();
            users.setUsername(username);
            users.setPassword(DigestUtils.sha1Hex(password));
            users.setName(nameSplit[0] != null ? nameSplit[0] : "");
            users.setSurname(nameSplit[1] != null ? nameSplit[1] : "");
            users.setDateOfBirthDay(BookStoreUtil.parseDate(dateOfBirth));
            usersService.save(users);
            result = BookStoreUtil.MESSAGE_SUCCESS;
        } catch (Exception e) {
            result = BookStoreUtil.MESSAGE_FAIL;
        }
        return result;
    }

}
