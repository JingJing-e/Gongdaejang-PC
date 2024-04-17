package com.icia.clientback.front.food.service;

import com.icia.clientback.front.food.dto.FoodOrderDetailDto;
import com.icia.clientback.front.food.dto.FoodOrderDto;
import com.icia.clientback.front.food.mapper.FoodOrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FoodOrderDetailService {
    @Autowired
    FoodOrderDetailMapper foodOrderDetailMapper;
    public void insert(FoodOrderDto foodOrder) {
        for(FoodOrderDetailDto data : foodOrder.getFoodOrderDetailList()){
            data.setFoodOrderId(foodOrder.getFoodOrderId());
        }
        foodOrderDetailMapper.insert(foodOrder.getFoodOrderDetailList());
    }
}
