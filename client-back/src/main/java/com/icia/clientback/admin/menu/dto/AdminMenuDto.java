package com.icia.clientback.admin.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMenuDto {
    public long id;
    public String title;
    public String path;
    public String useYn;
    public long createId;
    public String createTime;
}
