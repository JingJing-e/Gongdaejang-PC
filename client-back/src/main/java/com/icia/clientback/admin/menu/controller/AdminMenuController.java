package com.icia.clientback.admin.menu.controller;

import com.icia.clientback.admin.menu.dto.AdminMenuDto;
import com.icia.clientback.admin.menu.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminMenuController {
    @Autowired
    AdminMenuService adminMenuService;

    @GetMapping("/getAdminMenu")
    public List<AdminMenuDto> getAdminMenu(){
        List<AdminMenuDto> adminMenuList = adminMenuService.getAdminMenu();
        return adminMenuList;
    }
}
