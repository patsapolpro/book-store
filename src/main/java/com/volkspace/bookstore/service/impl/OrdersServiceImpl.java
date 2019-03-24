package com.volkspace.bookstore.service.impl;

import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.repository.OrdersRepository;
import com.volkspace.bookstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public void save(Orders orders) {
        ordersRepository.save(orders);
    }

    @Override
    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public void deleteAll() {
        ordersRepository.deleteAll();
    }
}
