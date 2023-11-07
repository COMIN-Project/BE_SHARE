package com.example.newcomin.service.impl;

import com.example.newcomin.entity.User;
import com.example.newcomin.repository.UserRepository;
import com.example.newcomin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
        public User getUserById(Long userId){
            Optional<User> optionalUser = userRepository.findById(userId);
            return optionalUser.get();
        }
        @Override
        public List<User> getAllUsers(){
            return userRepository.findAll();
        }

    @Override
    public User updateUser(User user){
        User existingUser = userRepository.findById(user.getUserId()).get();
        existingUser.setUserEmail(user.getUserEmail());
        existingUser.setUserName(user.getUserName());
        existingUser.setUserRole(user.getUserRole());
        User updatedUser = userRepository.save(existingUser);
        return  updatedUser;
    }

    @Override
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
