package com.unionpay.pager.utils;

public class BusinessTypeUtils {
    public static int transform(String type){
        int num = 0;
        switch (type){
            case "移动支付业务":
                num = 1;
                break;
            case "无卡支付业务":
                num = 2;
                break;
            case "付款业务":
                num = 3;
                break;
            case "缴费业务":
                num = 4;
                break;
            case "创新业务":
                num = 5;
                break;
            case "POS业务":
                num = 6;
                break;
            case "POS":
                num = 7;
                break;
            case "银联二维码":
                num = 8;
                break;
            case "手机闪付":
                num = 9;
                break;
            case "银联在线支付（PC、WAP、手机控件）":
                num = 10;
                break;
            case "银联侧无跳转":
                num = 11;
                break;
            case "商户侧无跳转":
                num = 12;
                break;
            case "B2B网银":
                num = 13;
                break;
            case "B2C网银":
                num = 14;
                break;
            case "批量代收":
                num = 15;
                break;
            case "实时代收":
                num = 16;
                break;
            case "订购":
                num = 17;
                break;
            case "综合认证":
                num = 18;
                break;
            case "全渠道贷记":
                num = 19;
                break;
            case "公共事业缴费":
                num = 20;
                break;
            case "信用卡还款":
                num = 21;
                break;
            case "手机充值":
                num = 22;
                break;
            case "财税库银":
                num = 23;
                break;
            case "交通罚没":
                num = 24;
                break;
            case "党费缴纳":
                num = 25;
                break;
            case "学费缴纳":
                num = 26;
                break;
            case "非税":
                num = 27;
                break;
            case "刷脸支付":
                num = 28;
                break;
            case "手机POS":
                num = 29;
                break;
            case "标准价格":
                num = 1;
                break;
            case "基准价格":
                num = 2;
                break;
            case "特殊价格":
                num = 3;
                break;
        }
        return num;
    }
}
