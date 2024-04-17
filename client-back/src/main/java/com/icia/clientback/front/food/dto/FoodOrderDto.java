package com.icia.clientback.front.food.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FoodOrderDto {
    public long id;
    public long memberId;
    public long pcNum;
    public String orderNum;
    public int totalPrice;
    public String payType;
    public int cashAmount;
    public String request;
    public String createTime;

    public long foodId;
    public long foodOrderId;
    public String foodName;
    public int foodPrice;
    public int foodQuantity;
    public String settlementYn;
    public String updateTime;
    public List<FoodOrderDetailDto> foodOrderDetailList;
}
