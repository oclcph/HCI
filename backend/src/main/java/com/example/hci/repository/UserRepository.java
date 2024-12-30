package com.example.hci.repository;

import com.example.hci.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);
    User findByPhoneAndPassword(String phone, String password);
    User findByNameAndPassword(String name, String password);
}
