package com.zmate.util;

public class GlobalVar {
    //根据CID获取币种的单价
    public static String getCoinPriceUrl = "https://coin.chainext.io/v1/coin_list_all";

    //根据CID获取基金成分币权重
    public static String getCoinWeight = "https://api.chainext.io/v1/weight";

    //手续费率
    public static double high_Rate = 0.0075;
    public static double medium_Rate = 0.0050;
    public static double low_Rate = 0.0025;

    //手续费分界线
    public static double boundary_High = 30;
    public static double boundary_Low = 10;
}
