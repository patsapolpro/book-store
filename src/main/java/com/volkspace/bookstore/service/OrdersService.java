package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Orders;

import java.util.List;

public interface OrdersService {
    void save(Orders orders);

    List<Orders> findAll();

    void deleteAll();
}
