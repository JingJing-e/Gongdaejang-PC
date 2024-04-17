package com.icia.clientback.admin.member.mapper;

import com.icia.clientback.admin.member.dto.AdminMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AdminMemberMapper {
    List<AdminMemberDto> getMember();
}
