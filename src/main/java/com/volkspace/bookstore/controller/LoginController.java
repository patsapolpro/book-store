package com.volkspace.bookstore.controller;

import com.volkspace.bookstore.model.Users;
import com.volkspace.bookstore.service.UsersService;
import com.volkspace.bookstore.util.BookStoreUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "Login", notes = "Login By Username and Password")
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   @ApiParam(value = "username", required = true) @RequestParam String username,
                                   @ApiParam(value = "password", required = true) @RequestParam String password) {
        String passwordHex = DigestUtils.sha256Hex(password);
        Users users = usersService.findByUsernameAndPassword(username, passwordHex);
        if(users != null){
            request.getSession().setAttribute(BookStoreUtil.SESSION_USER_ID, users.getId());
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }
}
