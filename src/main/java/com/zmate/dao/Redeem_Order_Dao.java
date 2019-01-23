package com.zmate.dao;

public interface Redeem_Order_Dao {
    public void SaveOne();
    public void findOne();
    public void findAll();
    public String queryRedeemOrder();
    public String queryConfirmedRedeemOrder();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
}
