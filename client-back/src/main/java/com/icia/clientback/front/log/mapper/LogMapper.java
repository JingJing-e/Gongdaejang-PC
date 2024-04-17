package com.icia.clientback.front.log.mapper;

import com.icia.clientback.front.log.dto.LogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void insertLog(LogDto log);

    void updateLog(LogDto logDto);
}
