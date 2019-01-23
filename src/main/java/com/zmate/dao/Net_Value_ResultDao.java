package com.zmate.dao;

public interface Net_Value_ResultDao {
    public void SaveOne(String today_Total_Net_Value,String today_Total_Portion,String today_IOPV);
    public void findOne();
    public void findAll();
    public void queryOne();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
}
