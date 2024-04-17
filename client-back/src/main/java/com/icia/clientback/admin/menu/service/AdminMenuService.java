package com.icia.clientback.admin.menu.service;

import com.icia.clientback.admin.menu.dto.AdminMenuDto;
import com.icia.clientback.admin.menu.mapper.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMenuService {
    @Autowired
    AdminMenuMapper adminMenuMapper;
    public List<AdminMenuDto> getAdminMenu() { return adminMenuMapper.getAdminMenu(); }
}
