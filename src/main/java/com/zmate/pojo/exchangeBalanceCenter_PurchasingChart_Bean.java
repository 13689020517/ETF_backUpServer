package com.zmate.pojo;

public class exchangeBalanceCenter_PurchasingChart_Bean {
    private String order_ID;
    private String tx_Hash;
    private String client_ID;
    private String purchasing_Value;
    private String fund_Recieved_Value;
    private String completed_Portion;
    private String actual_Single_Price;
    private String evaluate_Purchasing_Value;
    private String today_Single_NetValue;
    private String evaluate_Portion;
    private String gas1;
    private String gas2;
    private String fee_Rate;
    private String fee;

    @Override
    public String toString() {
        return "exchangeBalanceCenter_PurchasingChart_Bean{" +
                "order_ID='" + order_ID + '\'' +
                ", tx_Hash='" + tx_Hash + '\'' +
                ", client_ID='" + client_ID + '\'' +
                ", purchasing_Value='" + purchasing_Value + '\'' +
                ", fund_Recieved_Value='" + fund_Recieved_Value + '\'' +
                ", completed_Portion='" + completed_Portion + '\'' +
                ", actual_Single_Price='" + actual_Single_Price + '\'' +
                ", evaluate_Purchasing_Value='" + evaluate_Purchasing_Value + '\'' +
                ", today_Single_NetValue='" + today_Single_NetValue + '\'' +
                ", evaluate_Portion='" + evaluate_Portion + '\'' +
                ", gas1='" + gas1 + '\'' +
                ", gas2='" + gas2 + '\'' +
                ", fee_Rate='" + fee_Rate + '\'' +
                ", fee='" + fee + '\'' +
                ", other_Fee='" + other_Fee + '\'' +
                ", submit_Date='" + submit_Date + '\'' +
                ", complete_Date='" + complete_Date + '\'' +
                '}';
    }

    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public String getTx_Hash() {
        return tx_Hash;
    }

    public void setTx_Hash(String tx_Hash) {
        this.tx_Hash = tx_Hash;
    }

    public String getClient_ID() {
        return client_ID;
    }

    public void setClient_ID(String client_ID) {
        this.client_ID = client_ID;
    }

    public String getPurchasing_Value() {
        return purchasing_Value;
    }

    public void setPurchasing_Value(String purchasing_Value) {
        this.purchasing_Value = purchasing_Value;
    }

    public String getFund_Recieved_Value() {
        return fund_Recieved_Value;
    }

    public void setFund_Recieved_Value(String fund_Recieved_Value) {
        this.fund_Recieved_Value = fund_Recieved_Value;
    }

    public String getCompleted_Portion() {
        return completed_Portion;
    }

    public void setCompleted_Portion(String completed_Portion) {
        this.completed_Portion = completed_Portion;
    }

    public String getActual_Single_Price() {
        return actual_Single_Price;
    }

    public void setActual_Single_Price(String actual_Single_Price) {
        this.actual_Single_Price = actual_Single_Price;
    }

    public String getEvaluate_Purchasing_Value() {
        return evaluate_Purchasing_Value;
    }

    public void setEvaluate_Purchasing_Value(String evaluate_Purchasing_Value) {
        this.evaluate_Purchasing_Value = evaluate_Purchasing_Value;
    }

    public String getToday_Single_NetValue() {
        return today_Single_NetValue;
    }

    public void setToday_Single_NetValue(String today_Single_NetValue) {
        this.today_Single_NetValue = today_Single_NetValue;
    }

    public String getEvaluate_Portion() {
        return evaluate_Portion;
    }

    public void setEvaluate_Portion(String evaluate_Portion) {
        this.evaluate_Portion = evaluate_Portion;
    }

    public String getGas1() {
        return gas1;
    }

    public void setGas1(String gas1) {
        this.gas1 = gas1;
    }

    public String getGas2() {
        return gas2;
    }

    public void setGas2(String gas2) {
        this.gas2 = gas2;
    }

    public String getFee_Rate() {
        return fee_Rate;
    }

    public void setFee_Rate(String fee_Rate) {
        this.fee_Rate = fee_Rate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOther_Fee() {
        return other_Fee;
    }

    public void setOther_Fee(String other_Fee) {
        this.other_Fee = other_Fee;
    }

    public String getSubmit_Date() {
        return submit_Date;
    }

    public void setSubmit_Date(String submit_Date) {
        this.submit_Date = submit_Date;
    }

    public String getComplete_Date() {
        return complete_Date;
    }

    public void setComplete_Date(String complete_Date) {
        this.complete_Date = complete_Date;
    }

    public exchangeBalanceCenter_PurchasingChart_Bean(String order_ID, String tx_Hash, String client_ID, String purchasing_Value, String fund_Recieved_Value, String completed_Portion, String actual_Single_Price, String evaluate_Purchasing_Value, String today_Single_NetValue, String evaluate_Portion, String gas1, String gas2, String fee_Rate, String fee, String other_Fee, String submit_Date, String complete_Date) {
        this.order_ID = order_ID;
        this.tx_Hash = tx_Hash;
        this.client_ID = client_ID;
        this.purchasing_Value = purchasing_Value;
        this.fund_Recieved_Value = fund_Recieved_Value;
        this.completed_Portion = completed_Portion;
        this.actual_Single_Price = actual_Single_Price;
        this.evaluate_Purchasing_Value = evaluate_Purchasing_Value;
        this.today_Single_NetValue = today_Single_NetValue;
        this.evaluate_Portion = evaluate_Portion;
        this.gas1 = gas1;
        this.gas2 = gas2;
        this.fee_Rate = fee_Rate;
        this.fee = fee;
        this.other_Fee = other_Fee;
        this.submit_Date = submit_Date;
        this.complete_Date = complete_Date;
    }

    private String other_Fee;
    private String submit_Date;
    private String complete_Date;
}
