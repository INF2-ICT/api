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
        if (userService.getAllUsers() != null) {
            return userService.getAllUsers();
        } else {
            List<UserModel> test = new ArrayList<>();

            test.add(new UserModel(1, 1, "Stefan", "Meijer", "stefan@meijer.nl", "welkom10"));
            test.add(new UserModel(2, 1, "Banko", "Town", "town@meijer.nl", "welkom10"));
            return test;
        }
    }

    @GetMapping("/user")
    public UserModel getUser(@RequestParam int id) {
        return userService.getUser(id); //return new UserModel(1, "Stefan", "Statenweg 13", 22);
    }
}
