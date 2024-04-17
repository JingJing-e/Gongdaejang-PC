package com.icia.clientback.admin.foods.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFoodsDto {
    public long id;
    public String name;
    public int price;
    public String useYn;
    public int sort;
    public String imgPath;
}
