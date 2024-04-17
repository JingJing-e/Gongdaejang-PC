package com.icia.clientback.front.member.mapper;

import com.icia.clientback.front.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void login(Member member);

    Member selectMember(Member member);

    void insertMember(Member member);

    int getDuplicateIdCount(Member member);

    void updateMember(Member member);
}
