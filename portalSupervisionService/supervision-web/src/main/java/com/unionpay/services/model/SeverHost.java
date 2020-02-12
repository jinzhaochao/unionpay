package com.unionpay.services.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigInteger;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 18:40
 * @Description:
 */
public class SeverHost extends BaseRowModel {
    @ExcelProperty(value = {"热词"}, index = 1)
    private String text;
    @ExcelProperty(value = {"频次"}, index = 2)
    private BigInteger cC;
    @ExcelProperty(value = {"状态"}, index = 3)
    private int isHot;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigInteger getcC() {
        return cC;
    }

    public void setcC(BigInteger cC) {
        this.cC = cC;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public SeverHost() {
        super();
    }

    public SeverHost(String text, BigInteger cC, int isHot) {
        this.text = text;
        this.cC = cC;
        this.isHot = isHot;
    }

    @Override
    public String toString() {
        return "SeverHost{" +
                "text='" + text + '\'' +
                ", cC=" + cC +
                ", isHot=" + isHot +
                '}';
    }
}
