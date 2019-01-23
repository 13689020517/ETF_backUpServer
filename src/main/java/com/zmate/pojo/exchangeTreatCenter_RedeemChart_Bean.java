package com.zmate.pojo;

public class exchangeTreatCenter_RedeemChart_Bean {
    private String tx_Hash;
    private String client_ID;
    private String reedeming_Value;
    private String fund_Recieved_Value;
    private String evaluate_Reedeming_Value;
    private String today_Net_Price;
    private String evaluate_Quotient;
    private String gas1;
    private String fee_Rate;
    private String fee;
    private String other_Fee;
    private String submit_Time;

    @Override
    public String toString() {
        return "exchangeTreatCenter_RedeemChart_Bean{" +
                "tx_Hash='" + tx_Hash + '\'' +
                ", client_ID='" + client_ID + '\'' +
                ", reedeming_Value='" + reedeming_Value + '\'' +
                ", fund_Recieved_Value='" + fund_Recieved_Value + '\'' +
                ", evaluate_Reedeming_Value='" + evaluate_Reedeming_Value + '\'' +
                ", today_Net_Price='" + today_Net_Price + '\'' +
                ", evaluate_Quotient='" + evaluate_Quotient + '\'' +
                ", gas1='" + gas1 + '\'' +
                ", fee_Rate='" + fee_Rate + '\'' +
                ", fee='" + fee + '\'' +
                ", other_Fee='" + other_Fee + '\'' +
                ", submit_Time='" + submit_Time + '\'' +
                '}';
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

    public String getReedeming_Value() {
        return reedeming_Value;
    }

    public void setReedeming_Value(String reedeming_Value) {
        this.reedeming_Value = reedeming_Value;
    }

    public String getFund_Recieved_Value() {
        return fund_Recieved_Value;
    }

    public void setFund_Recieved_Value(String fund_Recieved_Value) {
        this.fund_Recieved_Value = fund_Recieved_Value;
    }

    public String getEvaluate_Reedeming_Value() {
        return evaluate_Reedeming_Value;
    }

    public void setEvaluate_Reedeming_Value(String evaluate_Reedeming_Value) {
        this.evaluate_Reedeming_Value = evaluate_Reedeming_Value;
    }

    public String getToday_Net_Price() {
        return today_Net_Price;
    }

    public void setToday_Net_Price(String today_Net_Price) {
        this.today_Net_Price = today_Net_Price;
    }

    public String getEvaluate_Quotient() {
        return evaluate_Quotient;
    }

    public void setEvaluate_Quotient(String evaluate_Quotient) {
        this.evaluate_Quotient = evaluate_Quotient;
    }

    public String getGas1() {
        return gas1;
    }

    public void setGas1(String gas1) {
        this.gas1 = gas1;
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

    public String getSubmit_Time() {
        return submit_Time;
    }

    public void setSubmit_Time(String submit_Time) {
        this.submit_Time = submit_Time;
    }

    public exchangeTreatCenter_RedeemChart_Bean(String tx_Hash, String client_ID, String reedeming_Value, String fund_Recieved_Value, String evaluate_Reedeming_Value, String today_Net_Price, String evaluate_Quotient, String gas1, String fee_Rate, String fee, String other_Fee, String submit_Time) {
        this.tx_Hash = tx_Hash;
        this.client_ID = client_ID;
        this.reedeming_Value = reedeming_Value;
        this.fund_Recieved_Value = fund_Recieved_Value;
        this.evaluate_Reedeming_Value = evaluate_Reedeming_Value;
        this.today_Net_Price = today_Net_Price;
        this.evaluate_Quotient = evaluate_Quotient;
        this.gas1 = gas1;
        this.fee_Rate = fee_Rate;
        this.fee = fee;
        this.other_Fee = other_Fee;
        this.submit_Time = submit_Time;
    }
}
