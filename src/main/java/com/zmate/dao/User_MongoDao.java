package com.zmate.dao;

import java.util.List;

public interface User_MongoDao {
    public void SaveOne();
    public void findOne();
    public void findAll();
    public void queryOne();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();
}
