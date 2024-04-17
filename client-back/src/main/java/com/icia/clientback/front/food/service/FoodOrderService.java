package com.icia.clientback.front.food.service;

import com.icia.clientback.front.food.dto.FoodOrderDto;
import com.icia.clientback.front.food.mapper.FoodOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodOrderService {
    @Autowired
    FoodOrderMapper foodOrderMapper;
    @Autowired
    FoodOrderDetailService foodOrderDetailService;

    public void insert(FoodOrderDto foodOrder) {
        createOrderNum();
        foodOrder.setOrderNum(selectOrderNum());
        foodOrderMapper.insert(foodOrder);
        foodOrder.setFoodOrderId(foodOrder.getId());
        foodOrderDetailService.insert(foodOrder);
    }

    private String selectOrderNum() {
        return foodOrderMapper.selectOrderNum();
    }

    public void createOrderNum (){
        foodOrderMapper.createOrderNum();
    }
}
