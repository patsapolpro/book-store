package com.volkspace.bookstore.repository;

import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    void deleteByUsers(Users users);
}
