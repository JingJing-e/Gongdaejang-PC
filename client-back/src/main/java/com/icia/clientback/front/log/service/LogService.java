package com.icia.clientback.front.log.service;

import com.icia.clientback.front.log.dto.LogDto;
import com.icia.clientback.front.log.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    LogMapper logMapper;

    public void insertLog(LogDto logDto) { logMapper.insertLog(logDto);
    }

    public void updateLog(LogDto logDto) { logMapper.updateLog(logDto);
    }
}
