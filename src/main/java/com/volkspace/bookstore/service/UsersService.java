package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Users;

import java.util.List;

public interface UsersService {
    void save(Users users);

    Users findByUsernameAndPassword(String username, String passwordHex);

    void deleteAll();
}
