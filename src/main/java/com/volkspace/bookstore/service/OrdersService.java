package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.model.Users;

import java.util.List;

public interface OrdersService {
    void save(Orders orders);

    List<Orders> findAll();

    void deleteAll();

    void deleteByUsers(Users users);
}
