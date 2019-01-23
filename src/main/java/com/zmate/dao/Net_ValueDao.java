package com.zmate.dao;

import com.zmate.pojo.net_Value_CoinType;
import org.bson.Document;

import java.util.List;

public interface Net_ValueDao {
    public void SaveOne(Document document);
    public void findOne();
    public void findAll();
    public void queryOne();
    public void updateOne();
    public void updateMany();
    public void deleteOne();
    public void deleteMany();

    public List<net_Value_CoinType.ComponetCoinBean> queryYesterdayCoinTypeFromNetValue();
}
