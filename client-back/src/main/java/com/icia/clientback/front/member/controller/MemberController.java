package com.icia.clientback.front.member.controller;

import com.icia.clientback.front.member.dto.Member;
import com.icia.clientback.front.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @PostMapping("/login")
    public ResponseEntity<?> login (HttpServletRequest request, @RequestBody Member member) {
        String msg = "failed";
        log.info("============ login start ============");
        log.info("아이디 : "+member.getLoginId()+ ", 패스워드 : "+member.getPassword() + ", 카드 : " + member.getCardNum());
        Member memberInfo = memberService.login(member);
        return ResponseEntity.ok(memberInfo);
    }

    /**
     * 일단 ci, di값은 얻을 수 없음. 추후 휴대폰인증 로직 완성되면 ci, di 인서트예정
     * @param member
     * @return
     */
    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @PostMapping("/join")
    public ResponseEntity<?> joinMember (@RequestBody Member member) {
        String msg = "failed";
        log.info("============ join start ============");
        member.setTypeCode("m10004");
        member.setStatusCode("s10001");
        member.setCi("ad$#@sadd;l3TGREregt,tl43wr");
        member.setDi("gsT#$%<fk21qeAdkvAk342R$#@sd");
        // TODO 핸드폰인증로직 완성되면 ci,di값 넣어주고 같은 사람이 여러번 가입 못하도록 막아야함.
        memberService.insertMember(member);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @GetMapping("/confirmId")
    public ResponseEntity<?> confirmId (Member member) {
        int dupNum = memberService.getDuplicateIdCount(member);
        return ResponseEntity.ok(dupNum);
    }

    @CrossOrigin(origins = {"app://.", "http://localhost:3001", "http://localhost:8081"})
    @PutMapping("")
    public ResponseEntity<?> updateMember (@RequestBody Member member) {
        memberService.updateMember(member);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
