package com.icia.clientback.admin.member.service;

import com.icia.clientback.admin.member.dto.AdminMemberDto;
import com.icia.clientback.admin.member.mapper.AdminMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminMemberService {
    @Autowired
    AdminMemberMapper adminMemberMapper;
    public List<AdminMemberDto> getMember() {
        return adminMemberMapper.getMember();
    }
}
