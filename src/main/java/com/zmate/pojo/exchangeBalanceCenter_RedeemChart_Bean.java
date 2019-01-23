package com.zmate.pojo;

public class exchangeBalanceCenter_RedeemChart_Bean {
    private String order_ID;
    private String tx_Hash;
    private String client_ID;
    private String redeem_Value;
    private String fund_should_Recieved_Value;
    private String redeem_Value_Calculate;
    private String fund_Recieved_Value;
    private String actual_Redeem_Value;
    private String today_Single_NetValue;

    @Override
    public String toString() {
        return "exchangeBalanceCenter_RedeemChart_Bean{" +
                "order_ID='" + order_ID + '\'' +
                ", tx_Hash='" + tx_Hash + '\'' +
                ", client_ID='" + client_ID + '\'' +
                ", redeem_Value='" + redeem_Value + '\'' +
                ", fund_should_Recieved_Value='" + fund_should_Recieved_Value + '\'' +
                ", redeem_Value_Calculate='" + redeem_Value_Calculate + '\'' +
                ", fund_Recieved_Value='" + fund_Recieved_Value + '\'' +
                ", actual_Redeem_Value='" + actual_Redeem_Value + '\'' +
                ", today_Single_NetValue='" + today_Single_NetValue + '\'' +
                ", evaluate_Total_Value='" + evaluate_Total_Value + '\'' +
                ", gas1='" + gas1 + '\'' +
                ", gas2='" + gas2 + '\'' +
                ", fee_Rate='" + fee_Rate + '\'' +
                ", fee='" + fee + '\'' +
                ", other_Fee='" + other_Fee + '\'' +
                ", submit_Date='" + submit_Date + '\'' +
                ", complete_Date='" + complete_Date + '\'' +
                '}';
    }

    private String evaluate_Total_Value;
    private String gas1;
    private String gas2;

    private String fee_Rate;
    private String fee;
    private String other_Fee;
    private String submit_Date;

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

    public String getRedeem_Value() {
        return redeem_Value;
    }

    public void setRedeem_Value(String redeem_Value) {
        this.redeem_Value = redeem_Value;
    }

    public String getFund_should_Recieved_Value() {
        return fund_should_Recieved_Value;
    }

    public void setFund_should_Recieved_Value(String fund_should_Recieved_Value) {
        this.fund_should_Recieved_Value = fund_should_Recieved_Value;
    }

    public String getRedeem_Value_Calculate() {
        return redeem_Value_Calculate;
    }

    public void setRedeem_Value_Calculate(String redeem_Value_Calculate) {
        this.redeem_Value_Calculate = redeem_Value_Calculate;
    }

    public String getFund_Recieved_Value() {
        return fund_Recieved_Value;
    }

    public void setFund_Recieved_Value(String fund_Recieved_Value) {
        this.fund_Recieved_Value = fund_Recieved_Value;
    }

    public String getActual_Redeem_Value() {
        return actual_Redeem_Value;
    }

    public void setActual_Redeem_Value(String actual_Redeem_Value) {
        this.actual_Redeem_Value = actual_Redeem_Value;
    }

    public String getToday_Single_NetValue() {
        return today_Single_NetValue;
    }

    public void setToday_Single_NetValue(String today_Single_NetValue) {
        this.today_Single_NetValue = today_Single_NetValue;
    }

    public String getEvaluate_Total_Value() {
        return evaluate_Total_Value;
    }

    public void setEvaluate_Total_Value(String evaluate_Total_Value) {
        this.evaluate_Total_Value = evaluate_Total_Value;
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

    private String complete_Date;

    public exchangeBalanceCenter_RedeemChart_Bean(String order_ID, String tx_Hash, String client_ID, String redeem_Value, String fund_should_Recieved_Value, String redeem_Value_Calculate, String fund_Recieved_Value, String actual_Redeem_Value, String today_Single_NetValue, String evaluate_Total_Value, String gas1, String gas2, String fee_Rate, String fee, String other_Fee, String submit_Date, String complete_Date) {
        this.order_ID = order_ID;
        this.tx_Hash = tx_Hash;
        this.client_ID = client_ID;
        this.redeem_Value = redeem_Value;
        this.fund_should_Recieved_Value = fund_should_Recieved_Value;
        this.redeem_Value_Calculate = redeem_Value_Calculate;
        this.fund_Recieved_Value = fund_Recieved_Value;
        this.actual_Redeem_Value = actual_Redeem_Value;
        this.today_Single_NetValue = today_Single_NetValue;
        this.evaluate_Total_Value = evaluate_Total_Value;
        this.gas1 = gas1;
        this.gas2 = gas2;
        this.fee_Rate = fee_Rate;
        this.fee = fee;
        this.other_Fee = other_Fee;
        this.submit_Date = submit_Date;
        this.complete_Date = complete_Date;
    }
}
