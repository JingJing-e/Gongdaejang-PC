package com.icia.clientback.admin.home.service;

import com.icia.clientback.admin.home.dto.HomeDto;
import com.icia.clientback.admin.home.mapper.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    HomeMapper homeMapper;
}
