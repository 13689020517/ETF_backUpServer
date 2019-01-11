package com.zmate.test;

import com.zmate.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JDBCtest {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findById() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("aa.findById",2);
        System.out.println("查出来的值为:"+user);
        sqlSession.close();
    }

    @Test
    public void findByName() throws IOException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("aa.findByName","xiaobo");
        System.out.println("查出来的值为:"+list.size());
        sqlSession.close();
    }

}
