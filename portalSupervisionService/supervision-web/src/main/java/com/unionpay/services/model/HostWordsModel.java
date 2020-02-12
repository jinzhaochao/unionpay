package com.unionpay.services.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 热词管理列表实体类
 * @author jinzhao
 * @date 2019/09/24
 */
public class HostWordsModel extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * 服务咨询 ID
     */
    //@ExcelProperty(value = { "序号" }, index = 0)
    private BigInteger suggestId;
    /*
     * 热词
     */
    @ExcelProperty(value = {"热词"}, index = 1)
    private String words;
    /*
     * 频次
     */
    @ExcelProperty(value = {"频次"}, index = 2)
    private Integer frequency;
    /*
     * 状态
     */
    @ExcelProperty(value = {"状态"}, index = 3)
    private String status;

    private BigInteger isHot;

    public HostWordsModel() {
    }

    public HostWordsModel(BigInteger suggestId, String words, Integer frequency, String status, BigInteger isHot) {
        this.suggestId = suggestId;
        this.words = words;
        this.frequency = frequency;
        this.status = status;
        this.isHot = isHot;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigInteger getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(BigInteger suggestId) {
        this.suggestId = suggestId;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigInteger getIsHot() {
        return isHot;
    }

    public void setIsHot(BigInteger isHot) {
        this.isHot = isHot;
    }

    @Override
    public String toString() {
        return "HostWordsModel{" +
                "suggestId=" + suggestId +
                ", words='" + words + '\'' +
                ", frequency=" + frequency +
                ", status='" + status + '\'' +
                ", isHot=" + isHot +
                '}';
    }
}
