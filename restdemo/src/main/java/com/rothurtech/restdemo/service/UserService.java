package com.rothurtech.restdemo.service;

import com.rothurtech.restdemo.Entity.User;
import com.rothurtech.restdemo.exception.UserAgeNotFoundException;
import com.rothurtech.restdemo.exception.UserNameNotFoundException;
import com.rothurtech.restdemo.exception.UserNotFoundException;
import com.rothurtech.restdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserService {

    //all final fields need to be initialized
    //we need to do dependency injection here
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "user", key = "#id")
    public User getById(Long id) {
        //return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
        log.info("caching id {}", id);
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getByName(String name) {
        List<User> users = userRepository.findAll();

        List<User> filter = users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        if(filter.isEmpty()) {
            throw new UserNameNotFoundException(name);
        }

        return filter;
    }

    public List<User> getByAge(Integer age) {
        List<User> users = userRepository.findAll();
        List<User> filter = users.stream().filter(user -> user.getAge() == age).collect(Collectors.toList());
        if(filter.isEmpty()) {
            throw new UserAgeNotFoundException(age);
        }

        return filter;
    }

    //transactional ensure atomicity
    //propagation ensures atomicity (all actions happen or none at all).
    //isolation ensures the action reads from data already committed to the db
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User updateUser(Long Id, User request) {
        User existingUser = getById(Id);
        existingUser.setName(request.getName());
        existingUser.setAge(request.getAge());
        existingUser.setSalary(request.getSalary());
        //existingUser.setGender(request.getGender());

        return userRepository.save(existingUser);
    }

    public User updateAge(Long Id, User request) {
        User existingUser = getById(Id);
        existingUser.setAge(request.getAge());

        return userRepository.save(existingUser);
    }

    public void deleteById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new  RuntimeException("User not found " + id);
        }
        userRepository.deleteById(id);
    }

}
