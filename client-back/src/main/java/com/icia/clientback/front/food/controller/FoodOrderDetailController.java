package com.icia.clientback.front.food.controller;

import com.icia.clientback.front.food.service.FoodOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FoodOrderDetailController {
    @Autowired
    FoodOrderDetailService foodOrderDetailService;

}
