package com.volkspace.bookstore.service;

import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.impl.OrdersServiceImpl;
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
public class OrdersServiceImplTest {

    @Autowired
    private OrdersServiceImpl ordersService;

    @Before
    public void setUp() throws Exception {
        ordersService.deleteAll();

        Orders orders = new Orders();
        orders.setBookId(1);
        ordersService.save(orders);
    }

    @Test
    public void testCreateOrders() {
        Orders orders = new Orders();
        orders.setBookId(999);
        ordersService.save(orders);

        List<Orders> ordersList = ordersService.findAll();
        Assert.assertThat(ordersList.size(), Matchers.equalTo(2));
    }

    @Test
    public void testDeleteAll() {
        ordersService.deleteAll();

        List<Orders> ordersList = ordersService.findAll();
        Assert.assertThat(ordersList.size(), Matchers.equalTo(0));
    }
}
