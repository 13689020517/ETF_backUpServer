package com.zmate.dao.impl;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import com.zmate.MainApp;
import com.zmate.dao.Net_Value_ChartDao;
import org.bson.Document;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;

@Component
public class Net_Value_ChartDaoImpl implements Net_Value_ChartDao {

    MongoCollection<Document> collection;
    MongoCollection<DBObject> collection1;

    public Net_Value_ChartDaoImpl() {
        collection = MainApp.database.getCollection("net_Value_Chart");
        collection1 = MainApp.database.getCollection("net_Value_Chart",DBObject.class);
    }

    /**
     * 存入一条数据【插入一个文档对象】
     */
    @Override
    public void SaveOne(String jsonData){
        /*Document document = new Document("date", "2018/01/15 14:03:50")
                .append("componet_Coin", Arrays.asList(
        new Document("coin_Type","BTC").append("fund_Total","2000").append("eth_Price","20ether").append("usd_Price","30$").append("total_USD","100$").append("total_ETH","300ether").append("single_Amount","200"),
        new Document("coin_Type","ETH").append("fund_Total","2000").append("eth_Price","20ether").append("usd_Price","30$").append("total_USD","100$").append("total_ETH","300ether").append("single_Amount","200"),
        new Document("coin_Type","XRP").append("fund_Total","2000").append("eth_Price","20ether").append("usd_Price","30$").append("total_USD","100$").append("total_ETH","300ether").append("single_Amount","200"),
        new Document("coin_Type","EOS").append("fund_Total","2000").append("eth_Price","20ether").append("usd_Price","30$").append("total_USD","100$").append("total_ETH","300ether").append("single_Amount","200")));*/

        DBObject bson = (DBObject) JSON.parse(jsonData);

        collection1.insertOne(bson);

        System.err.println("count of documents from collection is :"+collection1.count());
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

        /**
         * 查询count == 1 的文档对象
         */
        Document doc = collection.find(eq("count", 1)).first();
        if(doc!=null){
            System.err.println("条件1查询结果："+doc.toJson());
        }else{
            System.err.println("条件1查询结果：无");
        }


        /**
         * 查询count<2  且 name.equals("Mongo")的文档对象,显然不存在
         * 如果查询全部符合条件的文档集合，请使用find(*).iterator()
         */
        doc = collection.find(and(lt("count",2),eq("name", "Mongo"))).first();
        if(doc!=null){
            System.err.println("条件2查询结果："+doc.toJson());
        }else{
            System.err.println("条件2查询结果：无");
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
     * 查询当天净值核算表格数据
     */
    @Override
    public String query_Today_NetValue_Chart(){
        /**
         * 查询指定日期的文档对象
         */
        Document doc = collection.find(eq("date", "2019/01/21")).first();
        if(doc!=null){
            System.err.println("条件1查询结果："+doc.toJson());
            return doc.toJson();
        }else{
            System.err.println("条件1查询结果：无");
            return "查询结果为空";
        }
    }

}
