package com.zmate.dao.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.zmate.MainApp;
import com.zmate.dao.Recall_OrderRecordDao;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Recall_OrderRecordDaoImpl implements Recall_OrderRecordDao {

    MongoCollection<Document> collection;
    MongoCollection<DBObject> collection1;

    public Recall_OrderRecordDaoImpl() {
        collection = MainApp.database1.getCollection("hash");
        collection1 = MainApp.database1.getCollection("hash",DBObject.class);
    }

    //查询当天回撤记录
    @Override
    public String queryRecallOrder() {
        //加入查询条件
        BasicDBObject query = new BasicDBObject();
        //时间区间查询 记住如果想根据这种形式进行时间的区间查询 ，存储的时候 记得把字段存成字符串，就按yyyy-MM-dd HH:mm:ss 格式来
        query.put("time", new BasicDBObject("$gte", "2018-12-15<br/> 00:00:01").append("$lte","2019-01-16<br/> 23:59:59"));
//        模糊查询
//        Pattern pattern = Pattern.compile("^.*王.*$", Pattern.CASE_INSENSITIVE);
//        query.put("userName", pattern);
        //精确查询
        BasicDBObject query1 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        query1.put("type", "buy");
        query2.put("type", "sell");
        BasicDBList list = new BasicDBList();
        list.add(query2);
        list.add(query1);

        query.put("$or", list);
        ArrayList<Document> documents = new ArrayList<>();
        Document result = new Document("data", documents);
        MongoCursor<Document> cursor = collection.find(query).iterator();
        try {
            /**
             * 如果迭代到下一个对象等于false，退出迭代，并关闭迭代器
             */
            int index = 0;
            while (cursor.hasNext()) {
                //System.err.println((index+1)+":"+cursor.next().toJson());
                documents.add(cursor.next());
                index++;
            }
            return result.toJson();
        } finally {
            cursor.close();
        }
    }

}
