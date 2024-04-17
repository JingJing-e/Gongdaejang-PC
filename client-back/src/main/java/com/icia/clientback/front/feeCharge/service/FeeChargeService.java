package com.icia.clientback.front.feeCharge.service;

import com.icia.clientback.front.feeCharge.dto.FeeChargeDto;
import com.icia.clientback.front.feeCharge.mapper.FeeChargeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeChargeService {
    @Autowired
    FeeChargeMapper feeChargeMapper;

    public List<FeeChargeDto> getList(FeeChargeDto feeChargeDto) {
        List<FeeChargeDto> feeChargeList = feeChargeMapper.getList(feeChargeDto);
        return feeChargeList;
    }
}
