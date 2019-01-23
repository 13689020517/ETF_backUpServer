package com.zmate.dao;

public interface Purchasing_Order_Dao {
    public void SaveOne();
    public void findOne();
    public void findAll();
    public String queryPurchasingOrder();
    public String queryConfirmedPurchasingOrder();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
}
