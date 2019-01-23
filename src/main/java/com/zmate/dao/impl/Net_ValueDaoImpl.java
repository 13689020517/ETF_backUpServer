package com.zmate.dao.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zmate.MainApp;
import com.zmate.dao.Net_ValueDao;
import com.zmate.pojo.net_Value_CoinType;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;

@Component
public class Net_ValueDaoImpl implements Net_ValueDao {

    MongoCollection<Document> collection;

    public Net_ValueDaoImpl() {
        collection = MainApp.database.getCollection("net_Value");
    }

    /**
     * 存入一条数据【插入一个文档对象】
     */
    @Override
    public void SaveOne(Document document){

        /*Document document = new Document("_id", "2018/01/14 19:30:30")
                .append("date", "2018/01/14")
                .append("componet_Coin",
                        Arrays.asList(
                                new Document("coin_Type","BTC").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",0).append("amount",2000),
                                new Document("coin_Type","ETH").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",1).append("amount",3000),
                                new Document("coin_Type","ETC").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",15).append("amount",2500),
                                new Document("coin_Type","LTC").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",4).append("amount",1600),
                                new Document("coin_Type","XRP").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",2).append("amount",2300),
                                new Document("coin_Type","XLM").append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",7).append("amount",3100)));
*/
        collection.insertOne(document);

        System.err.println("count of documents from collection is :"+collection.count());
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

        Document doc = collection.find(eq("date", "2019/01/14")).first();
        if(doc!=null){
            System.err.println("查询结果："+doc.toJson());
            String jsonString = doc.toJson();
            net_Value_CoinType net_Value_CoinType = JSON.parseObject(jsonString, net_Value_CoinType.class);
            System.out.println(net_Value_CoinType.get_id());
            System.out.println(net_Value_CoinType.getDate());
            System.out.println(net_Value_CoinType.getComponet_Coin().size());
        }else{
            System.err.println("查询结果：无");
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



    /**
     * 查询昨天的netvalue表中有哪些币种
     */
    @Override
    public List<net_Value_CoinType.ComponetCoinBean> queryYesterdayCoinTypeFromNetValue(){

        Document doc = collection.find(eq("date", "2019/01/14")).first();
        if(doc!=null){
            System.err.println("查询结果："+doc.toJson());
            String jsonString = doc.toJson();
            net_Value_CoinType net_Value_CoinType = JSON.parseObject(jsonString, net_Value_CoinType.class);
            return net_Value_CoinType.getComponet_Coin();
        }else{
            System.err.println("查询结果：无");
            return null;
        }
    }

}
