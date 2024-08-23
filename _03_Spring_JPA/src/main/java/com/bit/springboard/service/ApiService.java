package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApiService {
    Member save(MemberDto memberDto);

    List<Member> findAll();

    Member findById(Long id);

    void deleteById(Long id);

    Member modify(Long id, MemberDto memberDto);

    FreeBoard post(BoardDto boardDto, MultipartFile[] uploadFiles);

    FreeBoard findFreeBoardById(Long id);
}
