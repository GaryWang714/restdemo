package com.rothurtech.restdemo.controller;

import com.rothurtech.restdemo.Entity.User;
import com.rothurtech.restdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.*;

@RestController
//base url
@RequestMapping("/api/users")
public class UserController {

//    User user = new User(1L, "bob", 20, 100.0);
//    User user2 = new User(2L, "bob2", 30, 120.0);
//
//    List<User> users = new ArrayList<>(Arrays.asList(user, user2));

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User newUser) {
        return userService.updateUser(id, newUser);
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable User user) {
        userService.deleteById(user.getId());
    }

}
