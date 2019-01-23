package com.zmate;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class MainApp {

    public static SqlSessionFactory sqlSessionFactory;

    public static MongoDatabase database;
    public static MongoDatabase database1;

    public static Jedis jedis;

    static {
        //加载mybatis配置文件
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载mongodb连接配置
        MongoClient mongoClient = new MongoClient("localhost" , 27017);
        database = mongoClient.getDatabase("accounting");

        MongoClient mongoClient1 = new MongoClient("localhost" , 27017);
        database1 = mongoClient1.getDatabase("zmt");

        //加载redis连接
        initRedis();
    }

    public static void main(String[] args) {
        showTimer();
        SpringApplication.run(MainApp.class, args);
    }

    private static void initRedis() {
        //加载redis连接配置
        //连接本地的 Redis 服务
        jedis = new Jedis("localhost");
        //查看redis服务是否正在运行
        System.err.println("服务正在运行: "+jedis.ping());
    }

    public static void showTimer() {
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,1);
            //每次启动都是早上9点
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),9,0,0);
            long timeInterval = 24 * 60 * 60 * 1000;
            //一天的间隔
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //TODO 需要做的事情
                    Calendar c=Calendar.getInstance();
                    SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    System.out.println("早上9点生成一次,当前时间为:"+f.format(c.getTime()));
                   }
                },calendar.getTime(),timeInterval);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
