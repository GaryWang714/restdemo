package com.rothurtech.restdemo.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String name) {
        super("user name not found for " + name);
    }
}
