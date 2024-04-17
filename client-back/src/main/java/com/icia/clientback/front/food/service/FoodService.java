package com.icia.clientback.front.food.service;

import com.icia.clientback.front.food.dto.FoodDto;
import com.icia.clientback.front.food.mapper.FoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FoodService {
    @Autowired
    FoodMapper foodMapper;

    public List<FoodDto> getList(FoodDto food) {
        List<FoodDto> foodList = foodMapper.getList(food);
        return foodList;
    }
}
