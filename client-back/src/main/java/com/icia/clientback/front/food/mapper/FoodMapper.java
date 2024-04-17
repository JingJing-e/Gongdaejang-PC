package com.icia.clientback.front.food.mapper;

import com.icia.clientback.front.food.dto.FoodDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodMapper {
    List<FoodDto> getList(FoodDto food);
}
