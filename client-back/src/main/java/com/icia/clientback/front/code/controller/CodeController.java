package com.icia.clientback.front.code.controller;

import com.icia.clientback.front.code.dto.CodeDto;
import com.icia.clientback.front.code.service.CodeService;
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
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeService codeService;

    @GetMapping("")
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    public ResponseEntity<?> get(CodeDto code){
        List<CodeDto> codeList = codeService.get(code);
        return ResponseEntity.ok(codeList);
    }
}
