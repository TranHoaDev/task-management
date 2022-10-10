package com.project.management.service.Impl;

import com.project.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.management.entity.UserAccount;
import com.project.management.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserAccount findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void save(UserAccount user) {
        userMapper.save(user);
    }
}
