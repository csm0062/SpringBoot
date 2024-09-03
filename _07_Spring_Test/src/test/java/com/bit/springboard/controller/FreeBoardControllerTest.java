package com.bit.springboard.controller;

import com.bit.springboard.config.SecurityConfiguration;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.entity.FreeBoard;
import com.bit.springboard.entity.Member;
import com.bit.springboard.handler.LoginFailureHandler;
import com.bit.springboard.oauth.OAuth2UserServiceImpl;
import com.bit.springboard.service.ApiService;
import com.bit.springboard.service.BoardService;
import com.bit.springboard.service.MemberService;
import com.bit.springboard.service.impl.FreeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(테스트컨트롤러클래스.class): 웹에서 사용하는 요청 url에 대한 테스트를 진행할 수 있다.
//                                      ()안에 지정된 클래스에 대해서만 테스트를 진행
@WebMvcTest(ApiController.class)
@Import(SecurityConfiguration.class)
public class FreeBoardControllerTest {
    @Autowired
    // 컨트롤러의 API를 테스트하기 위한 클래스
    // 서블릿 컨테이너의 구동 없이도 가상의 MVC 환경에서 HTTP서블릿을 요청하기 위한 객체
    private MockMvc mockMvc;

    // bean 객체를 자동으로 찾아다 넣어준다.
    @MockBean
    private ApiService apiService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private LoginFailureHandler loginFailureHandler;

    @MockBean
    private OAuth2UserServiceImpl oAuth2UserService;
    @Test
    void findByIdTest() throws Exception {
        // 테스트 계획 수립
        given(apiService.findFreeBoardById(1L))
                .willReturn(
                        FreeBoard.builder()
                                .id(1L)
                                .title("자유게시판 1번")
                                .content("이게 1번이올시다!!!")
                                .regdate(LocalDateTime.parse("2024-08-27T10:01:31.488988"))
                                .moddate(LocalDateTime.parse("2024-08-27T10:12:28.764544"))
                                .cnt(4)
                                .member(Member.builder()
                                        .id(1L)
                                        .nickname("soom")
                                        .build())
                                .boardFileList(new ArrayList<>())
                                .build()
                );

        // perform(): 요청 url을 테스트 할 수 있는 메소드
        mockMvc.perform(get("/apis/boards/" + 1))
                // andExpext(): 결과값을 검증
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("자유게시판 1번"))
                .andExpect(jsonPath("$.data.content").value("이게 1번이올시다!!!"))
                // andDo(print()): 응답 내용 전체 출력
                .andDo(print());
    }




}
