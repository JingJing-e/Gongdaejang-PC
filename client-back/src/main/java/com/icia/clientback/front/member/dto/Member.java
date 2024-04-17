package com.icia.clientback.front.member.dto;

import com.icia.clientback.core.annotation.Encrypt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    public long id;
    public String loginId;
    public @Encrypt String password;
    public String cardNum;
    public String birthDate;
    public String name;
    public @Encrypt String mobilePhoneNum;
    public String address;
    public String genderCode;
    public String typeCode;
    public String statusCode;
    public String ci;
    public String di;
    public String createTime;
    public String remainTime;
}
