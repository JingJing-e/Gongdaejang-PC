package com.icia.clientback.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchService {
    @Autowired BatchMapper batchMapper;

//    /**
//     * 회원 남은 시간, 로그 관리
//     * 1분마다 실행
//     * 테스트 이외에는 항상 주석처리
//     */
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void log() {
//        log.info("[ 0 */1 * * * * ] BatchService.log");
//        batchMapper.log();
//    }
}
