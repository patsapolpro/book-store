package com.volkspace.bookstore.controller;

import com.google.gson.Gson;
import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.BookStoreService;
import com.volkspace.bookstore.service.OrdersService;
import com.volkspace.bookstore.service.UsersService;
import com.volkspace.bookstore.util.BookStoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {

    @Autowired
    @Qualifier("databaseBookStoreService")
    private BookStoreService bookStoreService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/users")
    public String createUsers(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam("date_of_birth") String dateOfBirth) {
        String result;
        try {
            String passwordHex = DigestUtils.sha256Hex(password);
            String[] nameSplit = username.split("\\.");

            Users users = new Users();
            users.setUsername(username);
            users.setPassword(passwordHex);
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

    @GetMapping("/users")
    public String getUsers(HttpServletRequest request){
        Integer userlogin = (Integer) request.getSession().getAttribute(BookStoreUtil.SESSION_USER_ID);
        Users users = usersService.findById(userlogin);
        if(users != null) {
            List<Integer> bookIdList = new ArrayList<>();
            for (Orders orders: users.getOrders()) {
                bookIdList.add(orders.getBookId());
            }

            Map<String, Object> map = new HashMap<>();
            map.put("name", users.getName());
            map.put("surname", users.getSurname());
            map.put("date_of_birth", users.getDateOfBirthDay());
            map.put("books", bookIdList);
            return new Gson().toJson(map);
        }
        return null;
    }

    @DeleteMapping("/users")
    public void deleteUsers(HttpServletRequest request) {
        usersService.deleteAll();
        ordersService.deleteAll();
        request.getSession().removeAttribute(BookStoreUtil.SESSION_USER_ID);
    }

    @PostMapping("/users/orders")
    public String ordersByUser(HttpServletRequest request,
                              @RequestParam("orders") List<Integer> ordersIdList) {
        Integer userlogin = (Integer) request.getSession().getAttribute(BookStoreUtil.SESSION_USER_ID);
        Users users = usersService.findById(userlogin);
        if(users != null) {
            List<Book> bookList = new ArrayList<>();
            for (Integer id: ordersIdList) {
                Book book = bookStoreService.findById(id);
                if(book != null) {
                    Orders orders = new Orders();
                    orders.setBookId(id);
                    orders.setUsers(users);
                    ordersService.save(orders);

                    bookList.add(book);
                }
            }

            Double total = 0.00;
            for (Book book: bookList) {
                total += book.getPrice();
            }

            Map<String, Double> map = new HashMap<>();
            map.put("price", total);
            return new Gson().toJson(map);
        }
        return null;
    }

}
