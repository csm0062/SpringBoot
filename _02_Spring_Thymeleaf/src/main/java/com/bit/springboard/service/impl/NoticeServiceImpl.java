package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.mapper.NoticeMapper;
import com.bit.springboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements BoardService {
    private final NoticeMapper noticeMapper;
    private final FileUtils fileUtils;

//    public NoticeServiceImpl(NoticeMapper noticeMapper, FileUtils fileUtils) {
//        this.noticeMapper = noticeMapper;
//        this.fileUtils = fileUtils;
//    }

    @Override
    public BoardDto post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        if(uploadFiles != null && uploadFiles.length > 0) {
            Arrays.stream(uploadFiles).forEach(file -> {
                if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) {
                    BoardFileDto boardFileDto = fileUtils.parserFileInfo(file, "notice/");

                    boardFileDtoList.add(boardFileDto);
                }
            });
        }

        noticeMapper.post(boardDto);

        if(boardFileDtoList.size() > 0) {
            boardFileDtoList.forEach(boardFileDto -> boardFileDto.setBoard_id(boardDto.getId()));
            noticeMapper.postFiles(boardFileDtoList);
        }

        return noticeMapper.findById(boardDto.getId());
    }

    @Override
    public List<BoardDto> findAll(Map<String, String> searchMap, Criteria cri) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("search", searchMap);

        cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());

        paramMap.put("cri", cri);

        return noticeMapper.findAll(paramMap);
    }

    @Override
    public BoardDto findById(int id) {
        return null;
    }

    @Override
    public List<BoardFileDto> findFilesById(int id) {
        return noticeMapper.findFilesById(id);
    }

    @Override
    public BoardDto modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles) {
        return null;
    }

    @Override
    public void updateBoardCnt(int id) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public int findTotalCnt(Map<String, String> searchMap) {
        return 0;
    }
}
