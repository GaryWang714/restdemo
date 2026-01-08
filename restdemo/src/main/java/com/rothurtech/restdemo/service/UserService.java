package com.rothurtech.restdemo.service;

import com.rothurtech.restdemo.Entity.User;
import com.rothurtech.restdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
    }

    public User updateUser(Long Id, User request) {
        User existingUser = getById(Id);
        existingUser.setName(request.getName());
        existingUser.setAge(request.getAge());
        existingUser.setSalary(request.getSalary());

        return userRepository.save(existingUser);
    }

    public void deleteById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new  RuntimeException("User not found " + id);
        }
        userRepository.deleteById(id);
    }

}
