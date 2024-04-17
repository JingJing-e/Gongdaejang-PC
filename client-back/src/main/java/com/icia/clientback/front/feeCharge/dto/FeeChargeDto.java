package com.icia.clientback.front.feeCharge.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeeChargeDto {
    public long id;
    public String name;
    public int time;
    public int price;
    public String useYn;
    public String deleteYn;
    public String createTime;
    public String updateTime;
    public long createId;
    public long updateId;
}
