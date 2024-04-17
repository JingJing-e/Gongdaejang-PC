package com.icia.clientback.front.code.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeDto {
    public long id;
    public String code;
    public String name;
    public int stp;
    public int groupId;
    public String createTime;


    public String groupCode;
}
