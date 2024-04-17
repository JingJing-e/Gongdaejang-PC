package com.icia.clientback.front.food.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOrderNumDto {
    public long id;
    public String orderNum;
    public String createTime;
}
