package com.bit.springboard.controller;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.dto.ResponseDto;
import com.bit.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.net.URI;
import java.util.List;

// @RestController = @Controller + @ResponseBody
@RestController
@RequestMapping("/apis")
@RequiredArgsConstructor
public class ApiController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<?> save(MemberDto memberDto) {
        ResponseDto<MemberDto> responseDto = new ResponseDto<>();
        try {
            MemberDto savedMemberDto = memberService.save(memberDto);
            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("CREATED");
            responseDto.setData(savedMemberDto);
            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members")
    public ResponseEntity<?> findAll() {
        ResponseDto<MemberDto> responseDto = new ResponseDto<>();

        try {
            List<MemberDto> memberDtoList = memberService.findAll();
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setDataList(memberDtoList);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        ResponseDto<MemberDto> responseDto = new ResponseDto<>();

        try {
            MemberDto memberDto = memberService.findById(id);

            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("OK");
            responseDto.setData(memberDto);

            return ResponseEntity.ok(responseDto);
        } catch(Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        ResponseDto<MemberDto> responseDto = new ResponseDto<>();

        try {
            memberService.remove(id);

            responseDto.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDto.setStatusMessage("NO_CONTENT");

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());

            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    @PatchMapping("/members/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") int id,
                                    // x-www-form-urlencoded형태는
                                    // @ModelAttribute나 @RequewsrParam으로
                                    // 데이터를 전송받아 사용했다.
                                    // 전송되는 데이터의 형태가
                                    // application/json 형태면
                                    // @RequrestBody 어노테이션을 사용한다.
                                    @RequestBody MemberDto memberDto) {
        ResponseDto<MemberDto> responseDto = new ResponseDto<>();

        try {
            memberDto.setId(id);
            MemberDto modifiedMemberDto = memberService.modify(memberDto);

            responseDto.setStatusCode((HttpStatus.OK.value()));
            responseDto.setStatusMessage("OK");
            responseDto.setData(modifiedMemberDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {

            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }










}
