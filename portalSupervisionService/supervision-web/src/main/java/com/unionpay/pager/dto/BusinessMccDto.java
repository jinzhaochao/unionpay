package com.unionpay.pager.dto;

public class BusinessMccDto {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BusinessMccDto{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
