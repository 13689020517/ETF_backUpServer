package com.zmate.dao;


import com.zmate.pojo.User;

import java.util.List;

public interface UserDAO {

    public User findById(Integer id);

    public List<User> findByName(String name);

    public void insertUser(User user);

}
