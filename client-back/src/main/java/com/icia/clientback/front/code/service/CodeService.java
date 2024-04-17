package com.icia.clientback.front.code.service;

import com.icia.clientback.front.code.dto.CodeDto;
import com.icia.clientback.front.code.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    @Autowired
    CodeMapper codeMapper;
    public List<CodeDto> get(CodeDto code) {
        List<CodeDto> codeList = codeMapper.get(code);
        return codeList;
    }
}
