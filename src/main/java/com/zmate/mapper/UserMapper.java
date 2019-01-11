package com.zmate.mapper;

import com.zmate.pojo.User;

import java.util.List;

public interface UserMapper {

    public User findById(Integer id);

    public List<User> findByName(String username);

    public void insertUser(User user);

}
