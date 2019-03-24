package com.volkspace.bookstore.controller;

import com.google.gson.Gson;
import com.volkspace.bookstore.model.Book;
import com.volkspace.bookstore.model.Orders;
import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.BookStoreService;
import com.volkspace.bookstore.service.OrdersService;
import com.volkspace.bookstore.service.UsersService;
import com.volkspace.bookstore.util.BookStoreUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "Create Users", notes = "Create Users By Username and Password")
    @PostMapping("/users")
    public ResponseEntity<?> createUsers(@ApiParam(value = "username", required = true) @RequestParam String username,
                              @ApiParam(value = "password", required = true) @RequestParam String password,
                              @ApiParam(value = "dateOfBirth") @RequestParam("date_of_birth") String dateOfBirth) {
        Users userExist = usersService.findByUsername(username);
        //check non-create username repeat
        if(userExist == null) {
            String passwordHex = DigestUtils.sha256Hex(password);
            String[] nameSplit = username.split("\\.");

            Users users = new Users();
            users.setUsername(username);
            users.setPassword(passwordHex);
            users.setName(nameSplit[0] != null ? nameSplit[0] : "");
            users.setSurname(nameSplit[1] != null ? nameSplit[1] : "");
            users.setDateOfBirthDay(dateOfBirth != null ? BookStoreUtil.parseDate(dateOfBirth) : null);
            usersService.save(users);
            return ResponseEntity.ok().build();
        } else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Get Users", notes = "Get Users store in session")
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

    @ApiOperation(value = "Delete Users", notes = "Delete all Users")
    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUsers(HttpServletRequest request) {
        Integer userLogin = (Integer) request.getSession().getAttribute(BookStoreUtil.SESSION_USER_ID);
        if(userLogin != null){
            Users users = usersService.findById(userLogin);
            usersService.deleteById(userLogin);
            ordersService.deleteByUsers(users);
            request.getSession().removeAttribute(BookStoreUtil.SESSION_USER_ID);
            return  ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create Order By Users", notes = "Orders book by Users")
    @PostMapping("/users/orders")
    public String ordersByUser(HttpServletRequest request,
                               @ApiParam(value = "orders", required = true) @RequestParam("orders") List<Integer> ordersIdList) {
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
