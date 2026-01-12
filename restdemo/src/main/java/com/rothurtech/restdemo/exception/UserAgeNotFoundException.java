package com.rothurtech.restdemo.exception;

public class UserAgeNotFoundException extends RuntimeException {
    public UserAgeNotFoundException(Integer age) {
        super("not users exist with age " + age);
    }
}
