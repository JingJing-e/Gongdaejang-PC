package com.icia.clientback.batch;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchMapper {
    void log();
}
