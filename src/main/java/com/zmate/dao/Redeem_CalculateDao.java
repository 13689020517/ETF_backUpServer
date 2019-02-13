package com.zmate.dao;

import org.bson.Document;

import java.util.List;

public interface Redeem_CalculateDao {
    public void SaveOne();
    public void SaveMany(List<Document> documents);
    public void findOne();
    public void findAll();
    public void queryOne();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
    public String queryByOrderID(String order_ID);
    public String queryByDate(String date);
}
