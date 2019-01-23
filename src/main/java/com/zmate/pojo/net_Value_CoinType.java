package com.zmate.pojo;

import java.util.List;

public class net_Value_CoinType {

    /**
     * _id : 2018/01/14 19:30:30
     * date : 2018/01/14
     * componet_Coin : [{"coin_Type":"BTC","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":0,"amount":2000},{"coin_Type":"ETH","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":1,"amount":3000},{"coin_Type":"ETC","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":15,"amount":2500},{"coin_Type":"LTC","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":4,"amount":1600},{"coin_Type":"XRP","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":2,"amount":2300},{"coin_Type":"XLM","address":"0xaaaaaaaaaaaaaaaaaaaaaaaaaaa","CID":7,"amount":3100}]
     */

    private String _id;
    private String date;
    private List<ComponetCoinBean> componet_Coin;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ComponetCoinBean> getComponet_Coin() {
        return componet_Coin;
    }

    public void setComponet_Coin(List<ComponetCoinBean> componet_Coin) {
        this.componet_Coin = componet_Coin;
    }

    public static class ComponetCoinBean {
        /**
         * coin_Type : BTC
         * address : 0xaaaaaaaaaaaaaaaaaaaaaaaaaaa
         * CID : 0
         * amount : 2000
         */

        private String coin_Type;
        private String address;
        private int CID;
        private int amount;
        private double weight;

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getCoin_Type() {
            return coin_Type;
        }

        public void setCoin_Type(String coin_Type) {
            this.coin_Type = coin_Type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCID() {
            return CID;
        }

        public void setCID(int CID) {
            this.CID = CID;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
