package com.zmate.controller;

import com.zmate.MainApp;
import com.zmate.dao.User_MongoDao;
import com.zmate.mapper.UserMapper;
import com.zmate.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/netValue")
public class netValueAccounting_Controller {

    @Autowired
    User_MongoDao umd;

    @GetMapping("/getComponentCoinPrice")
    public String getComponentCoinPrice() {
        umd.deleteMany();
        return "操作mongodb成功";
    }

    @GetMapping("/testMysql")
    public String testMysql() {
        SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int xiaobo2 = userMapper.deleteUser(1);
        sqlSession.commit();
        sqlSession.close();
        return xiaobo2+"";
    }

}
