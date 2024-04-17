package com.icia.clientback.front.food.controller;

import com.icia.clientback.front.food.dto.FoodDto;
import com.icia.clientback.front.food.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @GetMapping("")
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    public ResponseEntity<?> getList(FoodDto food){
        List<FoodDto> foodList = foodService.getList(food);
        return ResponseEntity.ok(foodList);
    }

}
