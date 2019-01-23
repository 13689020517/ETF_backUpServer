package com.zmate.pojo;

import java.util.List;

public class coin_Price_Bean {

    /**
     * code : 1000
     * msg :
     * data : [{"cid":0,"symbol":"BTC","cmc_symbol":"bitcoin","latest":3593.0205290755657,"update_time":1547545784}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cid : 0
         * symbol : BTC
         * cmc_symbol : bitcoin
         * latest : 3593.0205290755657
         * update_time : 1547545784
         */

        private int cid;
        private String symbol;
        private String cmc_symbol;
        private double latest;
        private int update_time;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getCmc_symbol() {
            return cmc_symbol;
        }

        public void setCmc_symbol(String cmc_symbol) {
            this.cmc_symbol = cmc_symbol;
        }

        public double getLatest() {
            return latest;
        }

        public void setLatest(double latest) {
            this.latest = latest;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}
