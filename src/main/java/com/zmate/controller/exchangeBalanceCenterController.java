package com.zmate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmate.MainApp;
import com.zmate.dao.*;
import com.zmate.mapper.exchangeBalanceCenter_PurchasingMapper;
import com.zmate.mapper.exchangeBalanceCenter_RedeemMapper;
import com.zmate.mapper.recallOrderRecord_Mapper;
import com.zmate.pojo.exchangeBalanceCenter_PurchasingChart_Bean;
import com.zmate.pojo.exchangeBalanceCenter_RedeemChart_Bean;
import com.zmate.pojo.recallOrder_Bean;
import com.zmate.util.GlobalVar;
import com.zmate.util.MathUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/exchangeBalance")
public class exchangeBalanceCenterController {
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
    @Autowired
    Recall_OrderRecordDao rord;

    @PostMapping("/updatePurchasingAndRedeemBalance")
    public String updatePurchasingAndRedeemBalance() {
        //开启新线程去更新交易对账中心的申购处理记录，赎回处理记录和撤回订单记录
        new Thread(new Runnable() {
            @Override
            public void run() {

                //更新交易对账中心申购表格
                saveExchangeBalanceCenter_PurchasingChart();

                //更新交易对账中心赎回表格
                saveExchangeBalanceCenter_RedeemChart();

                //更新交易对账中心撤回订单表格
                saveExchangeBalanceCenter_RecallChart();
            }
        }).start();
        return "操作updatePurchasingAndRedeemBalance成功";
    }

    //更新交易对账中心申购表格的方法
    private void saveExchangeBalanceCenter_PurchasingChart() {
        //暂时将矿工费写死，需要用web3j从以太坊主网上获得
        double gas1 = 0.00009;
        double gas2 = 0.00008;

        //1.取出当天所有已经确认的申购记录
        String result = pod.queryConfirmedPurchasingOrder();
        //2.对已经确认的申购记录集合进行遍历，并将遍历的结果拼成可传入mapper的参数形式
        List<exchangeBalanceCenter_PurchasingChart_Bean> list = new ArrayList<>();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = (JSONArray) tempObj.get("data");

        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            String order_ID = (String) obj.get("_id");
            String tx_Hash = (String) obj.get("hash_1");
            String client_ID = (String) obj.get("account");
            double purchasing_Value = Double.parseDouble((String) obj.get("value"));
            double fund_Recieved_Value = (purchasing_Value-gas1-gas2);
            double fee_Rate = 0;
            double fee = 0;
            if(purchasing_Value >= GlobalVar.boundary_High){
                fee_Rate = GlobalVar.low_Rate;
                fee = purchasing_Value*fee_Rate;
            }else if(purchasing_Value < GlobalVar.boundary_High && purchasing_Value >=GlobalVar.boundary_Low){
                fee_Rate = GlobalVar.medium_Rate;
                fee = purchasing_Value*fee_Rate;
            }else {
                fee_Rate = GlobalVar.high_Rate;
                fee = purchasing_Value*fee_Rate;
            }
            System.out.println("order_ID:"+order_ID);
            String json_Obj = pcd.queryByOrderID(order_ID);
            String completed_Portion = (String) (JSON.parseObject(json_Obj)).get("my_Zmt_Value");
            String actual_Single_Price = ""+MathUtils.div(purchasing_Value+"",completed_Portion,4);

            double evaluate_Purchasing_Value = purchasing_Value-gas1-gas2-fee;
            String today_Single_NetValue = MainApp.jedis.get("today_IOPV");
            String evaluate_Portion = ""+MathUtils.div(evaluate_Purchasing_Value+"",today_Single_NetValue,4);
            String other_Fee = "0";
            String submit_Date = (String) obj.get("time");
            String complete_Date = (String) obj.get("nowstage_time5");
            exchangeBalanceCenter_PurchasingChart_Bean bean =
                    new exchangeBalanceCenter_PurchasingChart_Bean(order_ID, tx_Hash, client_ID, purchasing_Value+"", fund_Recieved_Value+"", completed_Portion, actual_Single_Price, evaluate_Purchasing_Value+"", today_Single_NetValue, evaluate_Portion, gas1+"", gas2+"", fee_Rate+"", fee+"", other_Fee, submit_Date, complete_Date);
            list.add(bean);
        }
        if(list.size() != 0){
            //3.将mysql更新，将list传入mapper中
            SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
            exchangeBalanceCenter_PurchasingMapper exchangeBalanceCenter_PurchasingMapper = sqlSession.getMapper(exchangeBalanceCenter_PurchasingMapper.class);
            int row = exchangeBalanceCenter_PurchasingMapper.batchInsert(list);
            sqlSession.commit();
            sqlSession.close();
        }
    }

    //更新交易对账中心赎回表格的方法
    private void saveExchangeBalanceCenter_RedeemChart() {
        //暂时将矿工费写死，需要用web3j从以太坊主网上获得
        double gas1 = 0.00009;
        double gas2 = 0.00008;

        //1.取出当天所有等待确认的赎回记录
        String result = rod.queryConfirmedRedeemOrder();
        System.err.println("result:"+result);
        //2.对已经确认的赎回记录集合进行遍历，并将遍历的结果拼成可传入mapper的参数形式
        List<exchangeBalanceCenter_RedeemChart_Bean> list = new ArrayList<>();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = (JSONArray) tempObj.get("data");

        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            String order_ID = (String) obj.get("_id");
            String tx_Hash = (String) obj.get("hash_1");
            String client_ID = (String) obj.get("account");
            double redeem_Value = Double.parseDouble((String) obj.get("value"));
            String today_Single_NetValue = MainApp.jedis.get("today_IOPV");
            double fund_should_Recieved_Value = (MathUtils.multi(redeem_Value+"",today_Single_NetValue,4)-gas1-gas2);
            double fee_Rate = 0;
            double fee = 0;
            if(redeem_Value >= GlobalVar.boundary_High){
                fee_Rate = GlobalVar.low_Rate;
                fee = redeem_Value*fee_Rate;
            }else if(redeem_Value < GlobalVar.boundary_High && redeem_Value >=GlobalVar.boundary_Low){
                fee_Rate = GlobalVar.medium_Rate;
                fee = redeem_Value*fee_Rate;
            }else {
                fee_Rate = GlobalVar.high_Rate;
                fee = redeem_Value*fee_Rate;
            }
            System.err.println("order_ID:"+order_ID);
            String json_Obj = rcd.queryByOrderID(order_ID);
            String redeem_Value_Calculate = (fund_should_Recieved_Value - fee)+"";
            String fund_Recieved_Value = ((BigDecimal)((JSON.parseObject(json_Obj)).get("my_Eth_Value"))).doubleValue()+"";
            String actual_Redeem_Value = ""+(Double.parseDouble(fund_Recieved_Value)-gas1-gas2-fee);

            String evaluate_Total_Value = ""+MathUtils.multi(redeem_Value+"",today_Single_NetValue,4);
            String other_Fee = "0";
            String submit_Date = (String) obj.get("time");
            String complete_Date = (String) obj.get("nowstage_time5");
            exchangeBalanceCenter_RedeemChart_Bean bean =
                    new exchangeBalanceCenter_RedeemChart_Bean(order_ID+"", tx_Hash+"", client_ID+"", redeem_Value+"", fund_should_Recieved_Value+"", redeem_Value_Calculate+"", fund_Recieved_Value+"", actual_Redeem_Value+"", today_Single_NetValue+"", evaluate_Total_Value+"", gas1+"", gas2+"", fee_Rate+"", fee+"", other_Fee+"", submit_Date+"", complete_Date+"");
            list.add(bean);
        }
        if(list.size() != 0){
            //3.将mysql更新，将list传入mapper中
            SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
            exchangeBalanceCenter_RedeemMapper exchangeBalanceCenter_RedeemMapper = sqlSession.getMapper(exchangeBalanceCenter_RedeemMapper.class);
            int row = exchangeBalanceCenter_RedeemMapper.batchInsert(list);
            sqlSession.commit();
            sqlSession.close();
        }

    }

    //更新交易对账中心撤回订单表格的方法
    private void saveExchangeBalanceCenter_RecallChart() {
        //暂时将矿工费写死，需要用web3j从以太坊主网上获得
        double gas1 = 0.00009;
        double gas2 = 0.00008;

        //1.取出当天所有申请撤回的申购和赎回记录
        String result = rord.queryRecallOrder();
        System.err.println("recallOrder:"+result);
        //2.对所有申请撤回申购赎回记录集合进行遍历，并将遍历的结果拼成可传入mapper的参数形式
        List<recallOrder_Bean> list = new ArrayList<>();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = (JSONArray) tempObj.get("data");

        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            String tx_Hash = (String) obj.get("hash_1");
            String client_ID = (String) obj.get("account");
            String tx_Type = (String) obj.get("type");
            String contract_Value = (String) obj.get("value");
            String contract_Coin_Type = "ZMT";
            String tx_Submit_Date = (String) obj.get("nowstage_time0");
            String tx_Recall_Date = (String) obj.get("nowstage_time1");
            String refund_Provide_Date = (String) obj.get("nowstage_time3");
            String get_Refund_Status = "无状态";
            String get_Refund_Date = "需要调用web3根据txhash得到blockhash,再用web3根据blockhash得到区块打包的时间";
            String actual_Get_Value = (Double.parseDouble(contract_Value)-gas1-gas2)+"";
            String actual_Get_CoinType = "";
            if(tx_Type.equals("buy")){
                actual_Get_CoinType = "ETH";
            }else if(tx_Type.equals("sell")){
                actual_Get_CoinType = "ZMT";
            }
            String status_Code = (String) obj.get("complete");
            Object temp =  obj.get("failure");
            boolean failure =false;
            if(temp != null){
                failure = (boolean) temp;
            }
            String order_Status = "";
            System.out.println("status_Code为:"+status_Code+"----"+"failure为:"+failure);
            if(status_Code.equals("3") && !failure){
                order_Status = "已完成";
            }else if(status_Code.equals("2")){
                order_Status = "异常中止";
            }else {
                order_Status = "进行中";
            }
            String abstract_MSG = "暂无摘要";
            recallOrder_Bean bean =
                    new recallOrder_Bean(tx_Hash,client_ID,tx_Type,contract_Value,contract_Coin_Type,tx_Submit_Date,tx_Recall_Date,refund_Provide_Date,get_Refund_Status,get_Refund_Date,actual_Get_Value,actual_Get_CoinType,order_Status,abstract_MSG);
            list.add(bean);
        }
        if(list.size() != 0){
            //3.将mysql更新，将list传入mapper中
            SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
            recallOrderRecord_Mapper recallOrderRecord_Mapper = sqlSession.getMapper(recallOrderRecord_Mapper.class);
            int row = recallOrderRecord_Mapper.batchInsert(list);
            sqlSession.commit();
            sqlSession.close();
        }
    }

}
