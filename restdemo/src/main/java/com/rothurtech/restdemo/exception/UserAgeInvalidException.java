package com.rothurtech.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAgeInvalidException extends RuntimeException {
    public UserAgeInvalidException(Integer age) {
        super("invalid user age " + age + "age should be > 0");
    }
}
