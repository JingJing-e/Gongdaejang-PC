package com.icia.clientback.front.food.controller;

import com.icia.clientback.front.food.dto.FoodOrderDto;
import com.icia.clientback.front.food.service.FoodOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class FoodOrderController {
    @Autowired
    FoodOrderService foodOrderService;
    @PostMapping("")
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    public ResponseEntity<?> insert(@RequestBody FoodOrderDto foodOrder) {
        log.info(foodOrder+"오더");
        foodOrderService.insert(foodOrder);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
