package com.volkspace.bookstore.service.impl;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.repository.UsersRepository;
import com.volkspace.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void save(Users users) {
        usersRepository.save(users);
    }

    @Override
    public Users findByUsernameAndPassword(String username, String passwordHex) {
        return usersRepository.findByUsernameAndPassword(username, passwordHex);
    }

    @Override
    public void deleteAll() {
        usersRepository.deleteAll();
    }

}
