package com.icia.clientback.front.feeCharge.controller;

import com.icia.clientback.front.feeCharge.dto.FeeChargeDto;
import com.icia.clientback.front.feeCharge.service.FeeChargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feeCharge")
public class FeeChargeController {
    @Autowired
    FeeChargeService feeChargeService;

    @GetMapping("")
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    public ResponseEntity<?> getList(FeeChargeDto feeChargeDto){
        List<FeeChargeDto> feeChargeList = feeChargeService.getList(feeChargeDto);
        return ResponseEntity.ok(feeChargeList);
    }
}
