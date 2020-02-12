package com.unionpay.pager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pager_business_dict")
public class BusinessDict implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer id;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "dict_type")
	private String dictType;
	private String name;
	private Integer value;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte status;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "BusinessDict{" +
                "id=" + id +
                ", dictType=" + dictType +
                ", name=" + name +
                ", value=" + value +
                ", status=" + status +
                ", sort=" + sort +
                '}';
    }
}
