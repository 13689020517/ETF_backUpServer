package com.zmate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmate.MainApp;
import com.zmate.dao.*;
import com.zmate.mapper.UserMapper;
import com.zmate.mapper.exchangeTreatCenter_PurchasingMapper;
import com.zmate.mapper.exchangeTreatCenter_RedeemMapper;
import com.zmate.mapper.purchasingAndRedeem_GapMapper;
import com.zmate.pojo.*;
import com.zmate.util.GlobalVar;
import com.zmate.util.HttpRequest;
import com.zmate.util.MathUtils;
import org.apache.ibatis.session.SqlSession;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/netValue")
public class netValueAccounting_Controller {

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

    @GetMapping("/testMysql")
    public String testMysql() {
        SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int xiaobo2 = userMapper.insertUser(new User(4,"哦哦"));
        sqlSession.commit();
        sqlSession.close();
        return xiaobo2+"";
    }

    @GetMapping("/test")
    public String test() {
        String result = pod.queryPurchasingOrder();
        System.out.println("test结果:"+result);
        return "成功！";
    }

    @PostMapping("/updateNetValue_And_TxCenter_Data")
    public String updateNetValue_And_TxCenter_Data(String result) {
        //开启新线程去更新净值核算表,净值核算结果表和交易处理中心表
        new Thread(new Runnable() {
            @Override
            public void run() {

                System.err.println("result为:"+result);

                //更新净值核算表格
                nvcd.SaveOne(result);

                //更新净值核算结果表格
                String today_IOPV = saveNetValue_Result(result);
                MainApp.jedis.set("today_IOPV",today_IOPV);
                purchasingAndRedeem_Gap_Bean gap_Bean = new purchasingAndRedeem_Gap_Bean();

                //更新交易处理中心申购表格
                gap_Bean = saveExchangeTreatCenter_PurchasingChart(today_IOPV,gap_Bean);

                //更新交易处理中心赎回表格
                gap_Bean = saveExchangeTreatCenter_RedeemChart(today_IOPV,gap_Bean);

                //更新申赎差额表格
                savePurchasingAndRedeem_GapChart(gap_Bean);

                //更新净值核算成分币库(即net_Value)
                saveNetValue();
            }
        }).start();
        return "操作save_NetValue_Chart_Data成功";
    }

    //更新净值核算成分币库(即net_Value)的方法
    private void saveNetValue() {
        String jsonResult = HttpRequest.sendGet(GlobalVar.getCoinWeight,"id="+2);
        System.err.println("新netValue:"+jsonResult);
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
        JSONObject jsonObject = JSON.parseObject(jsonResult);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList<Document> docuList = new ArrayList<Document>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            docuList.add(new Document("coin_Type",obj.get("symbol")).append("address","0xaaaaaaaaaaaaaaaaaaaaaaaaaaa").append("CID",obj.get("CID")).append("amount",2000).append("weight",obj.get("weight")+""));
        }
        Document document = new Document("date", f.format(c.getTime()))
                .append("componet_Coin", docuList);
        nvd.SaveOne(document);
    }

    //更新净值核算结果表格的方法
    private String saveNetValue_Result(String result) {
        JSONObject jsonObject = JSON.parseObject(result);
        //获取T日累计净值
        JSONArray componet_Coin = jsonObject.getJSONArray("componet_Coin");
        double total_ETH_Value = 0;
        for (int i = 0; i < componet_Coin.size(); i++) {
            JSONObject tempObj = (JSONObject) componet_Coin.get(i);
            double temp_Eth_Value = Double.valueOf(tempObj.get("total_ETH")+"");
            total_ETH_Value = total_ETH_Value + temp_Eth_Value;
        }

        //获取T日总份额(需要通过webj去合约中获取,暂时写死)
        int total_ZMT_Value = 200;

        //获取T日IOPV
        String today_IOPV = ""+MathUtils.div(total_ETH_Value+"",total_ZMT_Value+"",4);

        //将T日累计净值,T日总份额,T日IOPV写入净值核算结果表中
        nvrd.SaveOne(total_ETH_Value+"",total_ZMT_Value+"",today_IOPV);
        return today_IOPV;
    }

    //更新交易处理中心申购表格的方法
    private purchasingAndRedeem_Gap_Bean saveExchangeTreatCenter_PurchasingChart(String today_IOPV,purchasingAndRedeem_Gap_Bean gap_Bean) {
        //申购表格总计栏
        int n_Purchasing = 0;
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        double sum4 = 0;
        double sum5 = 0;
        double average_Sum = 0;
        double average1 = 0;
        double sum7 =0;
        double sum8 =0;

        //暂时将矿工费写死，需要用web3j从以太坊主网上获得
        double gas1 = 0.00009;

        //1.取出当天所有等待确认的申购记录
        String result = pod.queryPurchasingOrder();

        //2.对等待确认的申购记录集合进行遍历，并将遍历的结果拼成可传入mapper的参数形式
        List<exchangeTreatCenter_PurchasingChart_Bean> list = new ArrayList<>();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = (JSONArray) tempObj.get("data");
        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            String tx_Hash = (String) obj.get("hash_1");
            String client_ID = (String) obj.get("account");
            double purchasing_Value = Double.parseDouble((String) obj.get("value"));
            double fund_Recieved_Value = (purchasing_Value-gas1);
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
            double evaluate_Purchasing_Value = purchasing_Value-gas1-fee;
            String today_Net_Price = today_IOPV;
            String evaluate_Quotient = ""+MathUtils.div((purchasing_Value-gas1-fee)+"",today_Net_Price,4);
            String other_Fee = "0";
            String submit_Time = (String) obj.get("time");
            exchangeTreatCenter_PurchasingChart_Bean bean =
                    new exchangeTreatCenter_PurchasingChart_Bean(tx_Hash+"",client_ID+"",purchasing_Value+"",fund_Recieved_Value+"",evaluate_Purchasing_Value+"",today_Net_Price+"",evaluate_Quotient+"",gas1+"",fee_Rate+"",fee+"",other_Fee+"",submit_Time+"");
            list.add(bean);

            //统计总计栏的数据
            n_Purchasing =  ++n_Purchasing;
            sum1 = purchasing_Value+sum1;
            sum2 = fund_Recieved_Value+sum2;
            sum3 = evaluate_Purchasing_Value+sum3;
            sum4 = Double.parseDouble(evaluate_Quotient)+sum4;
            sum5 = gas1+sum5;
            average_Sum = average_Sum+fee_Rate;
            sum7 = sum7+fee;
            sum8 = sum8+Double.parseDouble(other_Fee);
        }
        average1 = MathUtils.div(average_Sum+"",n_Purchasing+"",4);

        //封装申赎差额对象
        gap_Bean.setN_Purchasing(n_Purchasing+"");
        gap_Bean.setSum1(sum1+"");
        gap_Bean.setSum2(sum2+"");
        gap_Bean.setSum3(sum3+"");
        gap_Bean.setIOPV(today_IOPV);
        gap_Bean.setSum4(sum4+"");
        gap_Bean.setSum5(sum5+"");
        gap_Bean.setAverage1(average1+"");
        gap_Bean.setSum7(sum7+"");
        gap_Bean.setSum8(sum8+"");

        if(list.size() != 0){
            //3.将mysql更新，将list传入mapper中
            SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
            exchangeTreatCenter_PurchasingMapper exchangeTreatCenter_PurchasingMapper = sqlSession.getMapper(exchangeTreatCenter_PurchasingMapper.class);
            int row = exchangeTreatCenter_PurchasingMapper.batchInsert(list);
            sqlSession.commit();
            sqlSession.close();
        }
        return gap_Bean;
    }

    //更新交易处理中心赎回表格的方法
    private purchasingAndRedeem_Gap_Bean saveExchangeTreatCenter_RedeemChart(String today_IOPV,purchasingAndRedeem_Gap_Bean gap_Bean) {
        //赎回表格总计栏
        int n_Redeem = 0;
        double sum9 = 0;
        double sum10 = 0;
        double sum11 = 0;
        double sum12 = 0;
        double sum13 = 0;
        double average_Sum = 0;
        double average2 = 0;
        double sum15 = 0;
        double sum16 = 0;

        //暂时将矿工费写死，需要用web3j从以太坊主网上获得
        double gas1 = 0.00009;

        //1.取出当天所有等待确认的申购记录
        String result = rod.queryRedeemOrder();

        //2.对等待确认的申购记录集合进行遍历，并将遍历的结果拼成可传入mapper的参数形式
        List<exchangeTreatCenter_RedeemChart_Bean> list = new ArrayList<>();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = (JSONArray) tempObj.get("data");
        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            String tx_Hash = (String) obj.get("hash_1");
            String client_ID = (String) obj.get("account");
            double redeem_Value = Double.parseDouble((String) obj.get("value"));
            double fund_Recieved_Value = (redeem_Value*Double.parseDouble(today_IOPV)-gas1);
            double fee_Rate = 0;
            double fee = 0;
            if(redeem_Value >= 30){
                fee_Rate = 0.0025;
                fee = redeem_Value*fee_Rate;
            }else if(redeem_Value < 30 && redeem_Value >=10){
                fee_Rate = 0.0050;
                fee = redeem_Value*fee_Rate;
            }else {
                fee_Rate = 0.0075;
                fee = redeem_Value*fee_Rate;
            }
            double evaluate_Redeem_Value = redeem_Value*Double.parseDouble(today_IOPV)-gas1-fee;
            String today_Net_Price = today_IOPV;
            double evaluate_Quotient = redeem_Value*Double.parseDouble(today_Net_Price);
            String other_Fee = "0";
            String submit_Time = (String) obj.get("time");
            exchangeTreatCenter_RedeemChart_Bean bean =
                    new exchangeTreatCenter_RedeemChart_Bean(tx_Hash+"",client_ID+"",redeem_Value+"",fund_Recieved_Value+"",evaluate_Redeem_Value+"",today_Net_Price+"",evaluate_Quotient+"",gas1+"",fee_Rate+"",fee+"",other_Fee+"",submit_Time+"");
            list.add(bean);

            //统计总计栏的数据
            n_Redeem = ++n_Redeem;
            sum9 = redeem_Value+sum9;
            sum10 = fund_Recieved_Value+sum10;
            sum11 = evaluate_Redeem_Value+sum11;
            sum12 = evaluate_Quotient+sum12;
            sum13 = gas1+sum13;
            average_Sum = average_Sum+fee_Rate;
            sum15 = fee+sum15;
            sum16 = sum16+Double.parseDouble(other_Fee);
        }
        average2 = MathUtils.div(average_Sum+"",n_Redeem+"",4);

        //封装申赎差额对象
        gap_Bean.setN_Redeem(n_Redeem+"");
        gap_Bean.setSum9(sum9+"");
        gap_Bean.setSum10(sum10+"");
        gap_Bean.setSum11(sum11+"");
        gap_Bean.setSum12(sum12+"");
        gap_Bean.setSum13(sum13+"");
        gap_Bean.setAverage2(average2+"");
        gap_Bean.setSum15(sum15+"");
        gap_Bean.setSum16(sum16+"");

        if(list.size() != 0){
            //3.将mysql更新，将list传入mapper中
            SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
            exchangeTreatCenter_RedeemMapper exchangeTreatCenter_RedeemMapper = sqlSession.getMapper(exchangeTreatCenter_RedeemMapper.class);
            int row = exchangeTreatCenter_RedeemMapper.batchInsert(list);
            sqlSession.commit();
            sqlSession.close();
        }

        return  gap_Bean;
    }

    //更新申赎差额表格的方法
    private void savePurchasingAndRedeem_GapChart(purchasingAndRedeem_Gap_Bean gap_Bean) {
        purchasingAndRedeem_GapChartResult_Bean gap_Chart_Bean = new purchasingAndRedeem_GapChartResult_Bean();
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        gap_Chart_Bean.setOrder_Date(f.format(c.getTime()));
        gap_Chart_Bean.setInitial_Purchasing_Value(gap_Bean.getSum1());
        gap_Chart_Bean.setFund_Total_Recieved(gap_Bean.getSum2());
        gap_Chart_Bean.setEvaluate_TotalPurchasing_Value(gap_Bean.getSum3());
        gap_Chart_Bean.setActual_Redeem_Value(gap_Bean.getSum9());
        gap_Chart_Bean.setEvaluate_Total_Value(gap_Bean.getSum12());
        gap_Chart_Bean.setFund_Should_Recieved_Value(gap_Bean.getSum10());
        gap_Chart_Bean.setEvaluate_Total_Redeem_Value(gap_Bean.getSum11());

        double gap_Calculate = Double.parseDouble(gap_Bean.getSum3())-Double.parseDouble(gap_Bean.getSum11());

        gap_Chart_Bean.setGap_Calculate(gap_Calculate+"");
        gap_Chart_Bean.setIOPV(gap_Bean.getIOPV());
        if(gap_Calculate > 0){
            gap_Chart_Bean.setShould_Purchasing_Gap(gap_Calculate+"");
            gap_Chart_Bean.setShould_Redeem_Gap("0");
        }else if(gap_Calculate < 0){
            gap_Chart_Bean.setShould_Purchasing_Gap("0");
            gap_Chart_Bean.setShould_Redeem_Gap(gap_Calculate+"");
        }else {
            gap_Chart_Bean.setShould_Purchasing_Gap("0");
            gap_Chart_Bean.setShould_Redeem_Gap("0");
        }
        gap_Chart_Bean.setEvaluate_Purchasing_Portion(""+Double.parseDouble(gap_Chart_Bean.getShould_Purchasing_Gap())/Double.parseDouble(gap_Bean.getIOPV()));
        gap_Chart_Bean.setEvaluate_Redeem_Portion(""+Double.parseDouble(gap_Chart_Bean.getShould_Redeem_Gap())/Double.parseDouble(gap_Bean.getIOPV()));
        double totalSinglePrice = getEvaluate_Redeem_Value();
        gap_Chart_Bean.setEvaluate_Redeem_Value(""+totalSinglePrice*(Double.parseDouble(gap_Chart_Bean.getShould_Redeem_Gap())/Double.parseDouble(gap_Bean.getIOPV())));

        SqlSession sqlSession = MainApp.sqlSessionFactory.openSession();
        purchasingAndRedeem_GapMapper purchasingAndRedeem_GapMapper = sqlSession.getMapper(purchasingAndRedeem_GapMapper.class);
        System.out.println("gap_Result结果为:"+gap_Chart_Bean.getActual_Redeem_Value());
        int row = purchasingAndRedeem_GapMapper.insert_Gap_Record(gap_Chart_Bean);
        sqlSession.commit();
        sqlSession.close();
    }

    private double getEvaluate_Redeem_Value() {
        String result = nvcd.query_Today_NetValue_Chart();
        JSONObject tempObj = JSON.parseObject(result);
        JSONArray tempArr = tempObj.getJSONArray("componet_Coin");
        double totalSinglePrice =0;
        for (int i = 0; i < tempArr.size(); i++) {
            JSONObject obj = (JSONObject) tempArr.get(i);
            BigDecimal single_Amount = MathUtils.objectConvertBigDecimal(obj.get("single_Amount"));
            BigDecimal eth_Price = MathUtils.objectConvertBigDecimal(obj.get("eth_Price"));
            totalSinglePrice += single_Amount.multiply(eth_Price).doubleValue();
        }
        return totalSinglePrice;
    }

    @GetMapping("/getNetValue_ComponetCoin_Chart")
    public Object getNetValue_ComponetCoin_Chart(){
        //获取ETH美金价格
        String eth_Bean = HttpRequest.sendGet(GlobalVar.getCoinPriceUrl,"id="+1);
        coin_Price_Bean eth_Obj = JSON.parseObject(eth_Bean, coin_Price_Bean.class);
        double eth_Temp = eth_Obj.getData().get(0).getLatest();

        List<net_Value_CoinType.ComponetCoinBean> list = nvd.queryYesterdayCoinTypeFromNetValue();
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();

        //求出当前日期
        Calendar c=Calendar.getInstance();
        SimpleDateFormat f=new SimpleDateFormat("yyyy/MM/dd");
        obj.put("date",f.format(c.getTime()));
        obj.put("componet_Coin",arr);

        if(list == null){
            JSONObject errObj = new JSONObject();
            errObj.put("code","1");
            errObj.put("msg","未查询到该天数据");
            return errObj;
        }

        for (net_Value_CoinType.ComponetCoinBean componetCoinBean : list) {
            String temp = HttpRequest.sendGet(GlobalVar.getCoinPriceUrl,"id="+componetCoinBean.getCID());
            coin_Price_Bean coin_price_bean = JSON.parseObject(temp, coin_Price_Bean.class);
            JSONObject tempObj = new JSONObject();

            String coin_type = componetCoinBean.getCoin_Type();
            double weight = componetCoinBean.getWeight();
            int fund_Total = componetCoinBean.getAmount();
            double eth_Price = coin_price_bean.getData().get(0).getLatest() / eth_Temp;
            double usd_Price = coin_price_bean.getData().get(0).getLatest();

            tempObj.put("coin_Type",coin_type);
            tempObj.put("fund_Total",fund_Total);
            tempObj.put("eth_Price",eth_Price);
            tempObj.put("usd_Price",usd_Price);
            tempObj.put("total_USD",fund_Total*usd_Price);
            tempObj.put("total_ETH",fund_Total*eth_Price);
            tempObj.put("single_Amount",weight/eth_Price);

            arr.add(tempObj);
        }
        return obj;
    }

}
