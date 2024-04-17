package com.icia.clientback.front.food.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodOrderDetailDto {
    public long id;
    public long foodId;
    public long foodOrderId;
    public String foodName;
    public int foodPrice;
    public int foodQuantity;
    public String settlementYn;
    public String createTime;
    public String updateTime;
}
