package com.icia.clientback.front.food.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodDto {
    public long id;
    public String name;
    public int price;
    public String typeCode;
    public int stock;
    public String useYn;
    public String recommendYn;
    public int sort;
    public String imgPath;
    public String createTime;
}
