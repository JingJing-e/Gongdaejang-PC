package com.icia.clientback.admin.member.controller;

import com.icia.clientback.admin.member.dto.AdminMemberDto;
import com.icia.clientback.admin.member.service.AdminMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adminMember")
public class AdminMemberController {
    @Autowired
    AdminMemberService adminMemberService;
    @GetMapping("")
    public String AdminMember (Model model){
        List<AdminMemberDto> adminMemberList = adminMemberService.getMember();
        model.addAttribute("adminMemberList",adminMemberList);
        return "memberManage";
    }

    @GetMapping("/member")
    public List<AdminMemberDto> getAdminMemberList (AdminMemberDto adminMemberDto) {
        List<AdminMemberDto> adminMemberList = adminMemberService.getMember();
        return adminMemberList;
    }
}
