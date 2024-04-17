package com.icia.clientback.admin.foods.service;

import com.icia.clientback.admin.foods.dto.AdminFoodsDto;
import com.icia.clientback.admin.foods.mapper.AdminFoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFoodsService {
    @Autowired
    AdminFoodsMapper adminFoodsMapper;
    public List<AdminFoodsDto> selectFood() {
        return adminFoodsMapper.selectFood();
    }
}
