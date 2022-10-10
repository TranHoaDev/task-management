package com.project.management.mapper;

import com.project.management.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserAccount findByUsername(String username);
    void save(@Param("user") UserAccount user);
}
