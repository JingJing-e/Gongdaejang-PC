package com.icia.clientback.admin.menu.mapper;

import com.icia.clientback.admin.menu.dto.AdminMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMenuMapper {
    List<AdminMenuDto> getAdminMenu();
}
