package com.icia.clientback.front.log.controller;

import com.icia.clientback.front.log.dto.LogDto;
import com.icia.clientback.front.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @PostMapping("")
    public ResponseEntity<?> insertLog(@RequestBody LogDto logDto){
        logService.insertLog(logDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @PutMapping("")
    public ResponseEntity<?> updateLog(@RequestBody LogDto logDto){
        logService.updateLog(logDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
