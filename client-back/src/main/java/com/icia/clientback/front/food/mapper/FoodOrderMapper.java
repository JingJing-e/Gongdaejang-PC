package com.icia.clientback.front.food.mapper;

import com.icia.clientback.front.food.dto.FoodOrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoodOrderMapper {
    void insert(FoodOrderDto foodOrder);

    void createOrderNum();

    String selectOrderNum();
}
