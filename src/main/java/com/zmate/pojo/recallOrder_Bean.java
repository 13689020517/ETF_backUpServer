package com.zmate.pojo;

public class recallOrder_Bean {
    private String tx_Hash;
    private String client_ID;
    private String tx_Type;
    private String contract_Value;
    private String contract_Coin_Type;
    private String tx_Submit_Date;
    private String tx_Recall_Date;
    private String refund_Provide_Date;
    private String get_Refund_Status;
    private String get_Refund_Date;
    private String actual_Get_Value;
    private String actual_Get_CoinType;
    private String order_Status;

    @Override
    public String toString() {
        return "recallOrder_Bean{" +
                "tx_Hash='" + tx_Hash + '\'' +
                ", client_ID='" + client_ID + '\'' +
                ", tx_Type='" + tx_Type + '\'' +
                ", contract_Value='" + contract_Value + '\'' +
                ", contract_Coin_Type='" + contract_Coin_Type + '\'' +
                ", tx_Submit_Date='" + tx_Submit_Date + '\'' +
                ", tx_Recall_Date='" + tx_Recall_Date + '\'' +
                ", refund_Provide_Date='" + refund_Provide_Date + '\'' +
                ", get_Refund_Status='" + get_Refund_Status + '\'' +
                ", get_Refund_Date='" + get_Refund_Date + '\'' +
                ", actual_Get_Value='" + actual_Get_Value + '\'' +
                ", actual_Get_CoinType='" + actual_Get_CoinType + '\'' +
                ", order_Status='" + order_Status + '\'' +
                ", abstract_MSG='" + abstract_MSG + '\'' +
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

    public String getTx_Type() {
        return tx_Type;
    }

    public void setTx_Type(String tx_Type) {
        this.tx_Type = tx_Type;
    }

    public String getContract_Value() {
        return contract_Value;
    }

    public void setContract_Value(String contract_Value) {
        this.contract_Value = contract_Value;
    }

    public String getContract_Coin_Type() {
        return contract_Coin_Type;
    }

    public void setContract_Coin_Type(String contract_Coin_Type) {
        this.contract_Coin_Type = contract_Coin_Type;
    }

    public String getTx_Submit_Date() {
        return tx_Submit_Date;
    }

    public void setTx_Submit_Date(String tx_Submit_Date) {
        this.tx_Submit_Date = tx_Submit_Date;
    }

    public String getTx_Recall_Date() {
        return tx_Recall_Date;
    }

    public void setTx_Recall_Date(String tx_Recall_Date) {
        this.tx_Recall_Date = tx_Recall_Date;
    }

    public String getRefund_Provide_Date() {
        return refund_Provide_Date;
    }

    public void setRefund_Provide_Date(String refund_Provide_Date) {
        this.refund_Provide_Date = refund_Provide_Date;
    }

    public String getGet_Refund_Status() {
        return get_Refund_Status;
    }

    public void setGet_Refund_Status(String get_Refund_Status) {
        this.get_Refund_Status = get_Refund_Status;
    }

    public String getGet_Refund_Date() {
        return get_Refund_Date;
    }

    public void setGet_Refund_Date(String get_Refund_Date) {
        this.get_Refund_Date = get_Refund_Date;
    }

    public String getActual_Get_Value() {
        return actual_Get_Value;
    }

    public void setActual_Get_Value(String actual_Get_Value) {
        this.actual_Get_Value = actual_Get_Value;
    }

    public String getActual_Get_CoinType() {
        return actual_Get_CoinType;
    }

    public void setActual_Get_CoinType(String actual_Get_CoinType) {
        this.actual_Get_CoinType = actual_Get_CoinType;
    }

    public String getOrder_Status() {
        return order_Status;
    }

    public void setOrder_Status(String order_Status) {
        this.order_Status = order_Status;
    }

    public String getAbstract_MSG() {
        return abstract_MSG;
    }

    public void setAbstract_MSG(String abstract_MSG) {
        this.abstract_MSG = abstract_MSG;
    }

    public recallOrder_Bean() {
    }

    private String abstract_MSG;

    public recallOrder_Bean(String tx_Hash, String client_ID, String tx_Type, String contract_Value, String contract_Coin_Type, String tx_Submit_Date, String tx_Recall_Date, String refund_Provide_Date, String get_Refund_Status, String get_Refund_Date, String actual_Get_Value, String actual_Get_CoinType, String order_Status, String abstract_MSG) {
        this.tx_Hash = tx_Hash;
        this.client_ID = client_ID;
        this.tx_Type = tx_Type;
        this.contract_Value = contract_Value;
        this.contract_Coin_Type = contract_Coin_Type;
        this.tx_Submit_Date = tx_Submit_Date;
        this.tx_Recall_Date = tx_Recall_Date;
        this.refund_Provide_Date = refund_Provide_Date;
        this.get_Refund_Status = get_Refund_Status;
        this.get_Refund_Date = get_Refund_Date;
        this.actual_Get_Value = actual_Get_Value;
        this.actual_Get_CoinType = actual_Get_CoinType;
        this.order_Status = order_Status;
        this.abstract_MSG = abstract_MSG;
    }
}
