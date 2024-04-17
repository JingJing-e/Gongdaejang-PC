package com.icia.clientback.admin.foods.mapper;

import com.icia.clientback.admin.foods.dto.AdminFoodsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminFoodsMapper {
    List<AdminFoodsDto> selectFood();
}
