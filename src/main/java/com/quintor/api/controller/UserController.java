package com.quintor.api.controller;

import com.quintor.api.model.UserModel;
import com.quintor.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List getAllUsers() throws SQLException {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public UserModel getUser(@RequestParam int id) {
        return userService.getUser(id); //return new UserModel(1, "Stefan", "Statenweg 13", 22);
    }
}
