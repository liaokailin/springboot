package com.lkl.springboot.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 订单实体
 * 
 * @author lkl
 * @version $Id: Order.java, v 0.1 2015年7月29日 下午2:28:48 lkl Exp $
 */
@JsonSerialize
public class Order {

    @NotNull
    private String  orderId;
    @Min(1)
    private Integer total;

    private String  address;
    private String  customerName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", total=" + total + ", address=" + address + ", customerName="
               + customerName + "]";
    }

}
