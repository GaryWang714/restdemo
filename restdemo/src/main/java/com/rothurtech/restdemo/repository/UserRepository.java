package com.rothurtech.restdemo.repository;

import com.rothurtech.restdemo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//first arg is entity, second arg is primary key data type
@Repository
public interface UserRepository extends JpaRepository<User, Long> {



}
