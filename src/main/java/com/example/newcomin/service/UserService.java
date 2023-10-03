package com.example.newcomin.service;

import com.example.newcomin.entity.User;
import java.util.List;


public interface UserService {
    User createUser(User user);

    User getUserById(Long uesrId);
    List<User> getAllUsers();

    User updateUser(User user);
}
