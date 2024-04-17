package com.icia.clientback.front.code.mapper;

import com.icia.clientback.front.code.dto.CodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CodeDto> get(CodeDto code);
}
