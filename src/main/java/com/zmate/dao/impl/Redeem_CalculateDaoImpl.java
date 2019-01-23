package com.zmate.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zmate.MainApp;
import com.zmate.dao.Redeem_CalculateDao;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;

@Component
public class Redeem_CalculateDaoImpl implements Redeem_CalculateDao {
    MongoCollection<Document> collection;

    public Redeem_CalculateDaoImpl() {
        collection = MainApp.database.getCollection("redeem_Calculate");
    }

    /**
     * 存入一条数据【插入一个文档对象】
     */
    @Override
    public void SaveOne(){
        /**
         * 数据（含文档套文档） :
         {
         "name" : "MongoDB",
         "type" : "nosqlDB",
         "count" : 1,
         "versions": [ "v3.6", "v3.0", "v2.6" ],
         "info" : { x : 203, y : 102 }
         }
         */
        Document document = new Document("date", "2018/03/03 10:20:11")
                .append("client_ID","xiaobo")
                .append("eth_Value","20")
                .append("zmt_Value","10")
                .append("componet_Coin", Arrays.asList(
                        new Document("BTC","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("ETH","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("ETC","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("LTC","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("XRP","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("USDT","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
                        new Document("XLM","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa")));

        collection.insertOne(document);
        System.err.println("count of documents from collection is :"+collection.count());
    }

    /**
     * 存入多条数据【插入多个文档对象】
     */
    @Override
    public void SaveMany(List<Document> documents){
        collection.insertMany(documents);
        System.err.println("批量插入多条记录成功!");
    }

    /**
     * 无条件取出（查询）第一个文档对象（第一条数据）
     */
    @Override
    public void findOne(){
        Document doc = collection.find().first();
        System.err.println("只取【test】集合中的第一个文档对象："+doc.toJson());
    }

    /**
     * 无条件取出（查询）所有的文档对象（所有数据）
     */
    @Override
    public void findAll(){
        /**
         * 要检索集合中的所有文档，我们将使用find()方法，而不需要任何参数。
         * 为了遍历结果，将iterator()方法链接到find()中。
         * 迭代器类似SQL的游标cursor
         */
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            /**
             * 如果迭代到下一个对象等于false，退出迭代，并关闭迭代器
             */
            int index = 0;
            while (cursor.hasNext()) {
                System.err.println((index+1)+":"+cursor.next().toJson());
                index++;
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * 带条件查询文档对象
     */
    @Override
    public void queryOne(){

        MongoCursor<Document> cursor = collection.find(eq("date", "2018/03/03 10:20:11")).iterator();
        try {
            /**
             * 如果迭代到下一个对象等于false，退出迭代，并关闭迭代器
             */
            int index = 0;
            while (cursor.hasNext()) {
                System.err.println((index+1)+":"+cursor.next().toJson());
                index++;
            }
        } finally {
            cursor.close();
        }

        /**
         * Filters
         * gt gte  === >  >=
         * lt lte  === <  <=
         * eq      === ==
         * and     === 相当于 sql语句的 where xxx  and xxx
         * or      === 相当于 sql语句的 where xxx  or  xxx
         */
    }

    /**
     * 更新匹配的第一条文档 -- ByOne
     */
    @Override
    public void updateOne(){

        /**
         * 要更新集合中的文档，使用集合的updateOne方法更新单个文档对象
         *
         * $set  相当于 sql语句的 update collection set type = "database" where name = "MongoDB" limit 1
         */
        UpdateResult result = collection.updateOne(eq("name", "MongoDB"), new Document("$set", new Document("type", "database")));
        System.err.println("更新影响的行数："+result.getModifiedCount());

    }

    /**
     * 批量更新所有匹配的文档 -- ByMany
     */
    @Override
    public void updateMany(){

        /**
         * 要更新集合中的文档，使用集合的updateMany方法更新所有符合条件的文档对象
         * 下面更新所有name等于"MongoDB"的文档对象的count属性 +100
         */
        UpdateResult result = collection.updateMany(eq("name", "MongoDB"), inc("count", 100));
        System.err.println("更新影响的行数："+result.getModifiedCount());
    }

    /**
     * 删除匹配的第一条文档 -- ByOne
     */
    @Override
    public void deleteOne(){
        /**
         * 删除第一个type.equals("nosqlDB")的文档对象
         */
        DeleteResult result = collection.deleteOne(eq("type", "nosqlDB"));
        System.err.println("删除影响的行数："+result.getDeletedCount());
    }

    /**
     * 批量删除所有匹配的文档 -- ByMany
     */
    @Override
    public void deleteMany(){

        /**
         * 删除所有count>=100的文档对象
         */
        DeleteResult result = collection.deleteMany(gte("count", 100));
        System.err.println("删除影响的行数："+result.getDeletedCount());
    }

    @Override
    public String queryByOrderID(String order_ID){
        Document doc = collection.find(eq("_id", order_ID)).first();
        return doc.toJson();
    }

}
