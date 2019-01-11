package com.zmate.dao.impl;

import com.zmate.dao.UserDAO;
import com.zmate.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private SqlSessionFactory sqlSessionFactory;

    public UserDAOImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public User findById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("aa.findById",id);
        sqlSession.close();
        return user;
    }

    public List<User> findByName(String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("aa.findByName",name);
        sqlSession.close();
        return list;
    }

    public void insertUser(User user) {

    }
}
