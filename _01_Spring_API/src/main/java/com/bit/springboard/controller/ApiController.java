package com.bit.springboard.controller;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// @RestController = @Controller + @ResponseBody(데이터를 뷰로가지않고 바로 리턴해버리는 어노테이션)

@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApiController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<?> save(MemberDto memberDto) {
        MemberDto savedMemberDto = memberService.save(memberDto);
        return ResponseEntity.ok(savedMemberDto);
    }
}
