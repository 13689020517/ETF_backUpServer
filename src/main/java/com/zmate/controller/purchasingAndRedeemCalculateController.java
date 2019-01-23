package com.zmate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmate.dao.*;
import com.zmate.util.MathUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/calculate")
public class purchasingAndRedeemCalculateController {
    @Autowired
    Net_ValueDao nvd;
    @Autowired
    Purchasing_CalculateDao pcd;
    @Autowired
    Net_Value_ResultDao nvrd;
    @Autowired
    Redeem_CalculateDao rcd;
    @Autowired
    Net_Value_ChartDao nvcd;
    @Autowired
    Purchasing_Order_Dao pod;
    @Autowired
    Redeem_Order_Dao rod;

    @PostMapping("/savePurchasingCalculateChart")
    public String savePurchasingCalculateChart(String result) {
        System.err.println("coinData1为:"+result);

        JSONObject tempResult = JSON.parseObject(result);
        JSONArray tempResultArr = (JSONArray) tempResult.get("coins");

        List<Document> documents = new ArrayList<Document>();
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
        String date = f.format(c.getTime());
        String total_Eth_Value = (String) tempResult.get("total_Eth_Value");
        String total_Zmt_Value = (String) tempResult.get("total_Zmt_Value");

        String data = pod.queryPurchasingOrder();
        JSONObject tempObj = JSON.parseObject(data);
        JSONArray tempArr = (JSONArray) tempObj.get("data");
        for (int i = 0; i < tempArr.size(); i++) {
            Document document = new Document();

            JSONObject obj = (JSONObject) tempArr.get(i);
            String my_Eth_Value = (String) obj.get("value");
            String account = (String) obj.get("account");
            String order_ID = (String) obj.get("_id");

            double rate = MathUtils.div(""+(Double.parseDouble(my_Eth_Value))/Math.pow(10,18),total_Eth_Value,2);
            double my_Zmt_Value = MathUtils.multi(total_Zmt_Value,rate+"",2);

            //MainApp.jedis.set(order_ID,my_Zmt_Value+"");
            document.append("_id",order_ID);
            document.append("date",date);
            document.append("account",account);
            document.append("my_Eth_Value",my_Eth_Value);
            document.append("total_Eth_Value",total_Eth_Value);
            document.append("my_Zmt_Value",my_Zmt_Value+"");
            document.append("total_Zmt_Value",total_Zmt_Value);

            List<Document> tempList = new ArrayList<Document>();
            document.append("componet_Coin",tempList);

            for (int i1 = 0; i1 < tempResultArr.size(); i1++) {
                Document document1 = new Document();
                JSONObject temp = (JSONObject)tempResultArr.get(i1);
                document1.append("coin_Type",temp.get("coinType"));
                document1.append("total_Value",temp.get("totalValue"));
                document1.append("my_Value",""+MathUtils.multi((String) temp.get("totalValue"),rate+"",4));
                tempList.add(document1);
            }
            documents.add(document);
        }

        pcd.SaveMany(documents);
        return "savePurchasingCalculateChart成功！";
    }

    @PostMapping("/saveRedeemCalculateChart")
    public String saveRedeemCalculateChart(String result) {
        System.err.println("coinData2为:"+result);

        JSONObject tempResult = JSON.parseObject(result);
        JSONArray tempResultArr = (JSONArray) tempResult.get("coins");

        List<Document> documents = new ArrayList<Document>();
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
        String date = f.format(c.getTime());
        String total_Eth_Value = (String) tempResult.get("total_Eth_Value");
        String total_Zmt_Value = (String) tempResult.get("total_Zmt_Value");

        String data = rod.queryRedeemOrder();
        JSONObject tempObj = JSON.parseObject(data);
        JSONArray tempArr = (JSONArray) tempObj.get("data");
        for (int i = 0; i < tempArr.size(); i++) {
            Document document = new Document();

            JSONObject obj = (JSONObject) tempArr.get(i);
            String my_Zmt_Value = (String) obj.get("value");
            String account = (String) obj.get("account");
            double rate = MathUtils.div(""+(Double.parseDouble(my_Zmt_Value))/Math.pow(10,6),total_Zmt_Value,2);
            double my_Eth_Value = MathUtils.multi(total_Eth_Value,rate+"",2);
            String order_ID = (String) obj.get("_id");

            //MainApp.jedis.set(order_ID,my_Eth_Value+"");
            document.append("_id",order_ID);
            document.append("date",date);
            document.append("account",account);
            document.append("my_Eth_Value",my_Eth_Value);
            document.append("total_Eth_Value",total_Eth_Value);
            document.append("my_Zmt_Value",my_Zmt_Value);
            document.append("total_Zmt_Value",total_Zmt_Value);
            document.append("hasPortion","1000");

            List<Document> tempList = new ArrayList<Document>();
            document.append("componet_Coin",tempList);

            for (int i1 = 0; i1 < tempResultArr.size(); i1++) {
                Document document1 = new Document();
                JSONObject temp = (JSONObject)tempResultArr.get(i1);
                document1.append("coin_Type",temp.get("coinType"));
                document1.append("total_Value",temp.get("totalValue"));
                document1.append("my_Value",""+MathUtils.multi((String) temp.get("totalValue"),rate+"",4));
                tempList.add(document1);
            }
            documents.add(document);
        }

        rcd.SaveMany(documents);
        return "saveRedeemCalculateChart成功！";
    }

}
