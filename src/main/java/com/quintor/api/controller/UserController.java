package com.quintor.api.controller;

import com.quintor.api.model.UserModel;
import com.quintor.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.quintor.api.util.ProjectConfigUtil.checkApiKey;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all-users")
    public List getAllUsers(@RequestHeader String apikey) throws Exception {
        //Check if api key is correct
        if (checkApiKey(apikey)) {
            return userService.getAllUsers(); // [ {}, {} ]
        } else {
            throw new Exception("Invalid API key");
        }
    }

    @GetMapping("/get-user")
    public UserModel getUser(@RequestHeader String apikey, @RequestParam int id) throws Exception {
        //Check if api key is correct
        if (checkApiKey(apikey)) {
            return userService.getUser(id); // {}
        } else {
            throw new Exception("Invalid API key");
        }
    }
}
