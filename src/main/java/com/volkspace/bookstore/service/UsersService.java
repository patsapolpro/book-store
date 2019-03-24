package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Users;

import java.util.List;

public interface UsersService {
    void save(Users users);

    List<Users> findAll();

    Users findByUsernameAndPassword(String username, String passwordHex);

    void deleteAll();

    Users findById(Integer id);
}
