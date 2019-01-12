package com.zmate.mapper;

import com.zmate.pojo.User;
import java.util.List;

public interface UserMapper {

    User findById(Integer id);

    List<User> findByName(String username);

    int insertUser(User user);

    int deleteUser(Integer id);

}
