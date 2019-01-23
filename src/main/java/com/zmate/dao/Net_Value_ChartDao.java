package com.zmate.dao;

public interface Net_Value_ChartDao {
    public void SaveOne(String jsonData);
    public void findOne();
    public void findAll();
    public void queryOne();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
    public String query_Today_NetValue_Chart();
}
