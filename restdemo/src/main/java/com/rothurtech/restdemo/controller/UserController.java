package com.rothurtech.restdemo.controller;

import com.rothurtech.restdemo.Entity.User;
import com.rothurtech.restdemo.exception.ApiError;
import com.rothurtech.restdemo.exception.UserNameNotFoundException;
import com.rothurtech.restdemo.exception.UserNotFoundException;
import com.rothurtech.restdemo.service.AsyncTest;
import com.rothurtech.restdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.time.Instant;
import java.util.*;

@Slf4j
//@Controller
@RestController
//base url
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AsyncTest asyncTest;

    @Autowired
    public UserController(UserService userService, AsyncTest asyncTest) {
        this.userService = userService;
        this.asyncTest = asyncTest;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false, defaultValue = "salary") String sort) {
        List<User> users = userService.getAllUsers();
        if(sort.equals("salary")) {
            Collections.sort(users, (u1, u2) -> Double.compare(u1.getSalary(), u2.getSalary()));
        }
        else if(sort.equals("age")) {
            Collections.sort(users, (u1, u2) -> Double.compare(u1.getAge(), u2.getAge()));
        }

        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        log.info("TEST LOG: update method was called with id {}", id);
        asyncTest.asyncTest(id);
        log.info("TEST LOG: update method finished with id {}", id);

        return user;
    }
    @GetMapping("/age/{age}")
    public List<User> getUserByAge(@PathVariable Integer age) {
        return userService.getByAge(age);
    }

    @GetMapping("/name")
    public List<User> getUsersByName(@RequestParam(required = false, defaultValue = "") String name) {
        if(name.isEmpty()) {
            throw new UserNameNotFoundException(name);
        }

        return userService.getByName(name);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody User newUser) {
        //log.info("TEST LOG: update method was called with id {}", id);
        return userService.updateUser(id, newUser);
    }

    @PutMapping("/age/{id}")
    public User updateAge(@PathVariable Long id, @Valid @RequestBody User newUser) {
        return userService.updateAge(id, newUser);
    }

    @PostMapping
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        //create user and return it? (4:18)
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable User user) {
        userService.deleteById(user.getId());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception e) {
        ApiError error = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }



}
