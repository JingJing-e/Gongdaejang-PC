package com.icia.clientback.front.feeCharge.mapper;

import com.icia.clientback.front.feeCharge.dto.FeeChargeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeeChargeMapper {
    List<FeeChargeDto> getList(FeeChargeDto feeChargeDto);
}
