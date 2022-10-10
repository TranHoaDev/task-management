package com.project.management.service;

import com.project.management.entity.UserAccount;

public interface UserService {
    UserAccount findByUsername(String username);

    void save(UserAccount user);
}
