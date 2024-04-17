package com.icia.clientback.front.member.service;

import com.icia.clientback.front.member.dto.Member;
import com.icia.clientback.front.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberMapper memberMapper;
    public Member login(Member member) {
        return memberMapper.selectMember(member);
    }

    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    public int getDuplicateIdCount(Member member) {
        return memberMapper.getDuplicateIdCount(member);
    }

    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }
}
