package com.icia.clientback.front.log.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogDto {
    public long id;
    public String loginTime;
    public String logoutTime;
    public long memberId;
    public int pcNum;
    public String typeCode;
    public String createTime;
}
