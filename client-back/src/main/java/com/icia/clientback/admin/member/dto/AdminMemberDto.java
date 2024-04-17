package com.icia.clientback.admin.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminMemberDto {
    public int id;
    public String loginId;
    public String name;
    public String genderCode;
    public String mobilePhoneNum;
    public String address;
    public int remain_time;
    public int status_code;
}
