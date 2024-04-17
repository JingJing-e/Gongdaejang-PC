package com.icia.clientback.front.food.mapper;

import com.icia.clientback.front.food.dto.FoodOrderDetailDto;
import com.icia.clientback.front.food.dto.FoodOrderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodOrderDetailMapper {
    void insert(List<FoodOrderDetailDto> foodOrder);
}
