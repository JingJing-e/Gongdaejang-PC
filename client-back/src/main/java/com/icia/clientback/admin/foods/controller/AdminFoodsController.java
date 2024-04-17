package com.icia.clientback.admin.foods.controller;

import com.icia.clientback.admin.foods.dto.AdminFoodsDto;
import com.icia.clientback.admin.foods.service.AdminFoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adminFoods")
public class AdminFoodsController {
    @Autowired
    AdminFoodsService adminFoodsService;
    @GetMapping("")
    public String AdminGoods (Model model){
        List<AdminFoodsDto> foodList = adminFoodsService.selectFood();
        model.addAttribute("foodList", foodList);
        return "foodsManage";
    }
}
