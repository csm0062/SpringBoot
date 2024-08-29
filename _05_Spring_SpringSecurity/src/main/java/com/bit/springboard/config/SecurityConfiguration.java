package com.bit.springboard.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity: SpringSecurity의 FilterChain을 구성하기 위한 어노테이션
@EnableWebSecurity
public class SecurityConfiguration {
    // 비밀번호 암호화 객체를 Bean 객체로 등록
    // 비밀번호 암호화 객체의 역할은 로그인할 때
    // 입력된 암호와 DB에 저장되어있는 암호화된 비밀번호가 일치하는지 비교.
    // 비밀번호 암호화 객체에 matches메소드(암호화 되지 않은 비밀번호, 암호화된 비밀번호)
    // 를 통해서 비교를 진행. 일치하면 true, 일치하지 않으면 false를 리턴한다.
    // 회원가입시 비밀번호 저장 전에 비밀번호를 암호화
    // 비밀번호는 한 번 암호화되면 다시는 복호화(디코딩)할 수 없다.

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 의 FilterChain 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // csrf공객에 대한 방지
                // csrf(Cross Site Request Forgery): 사이트 간 위조 요청을 하는 공격
                //                                  정상적인 사용자가 의도치 않게 위조요청을 보낼 수 있어서 꺼두면 안된다.
                //                                  논 브라우저 클라이언트(REST API 역할만 하는 어플리케이션)에서는 꺼둬도 무방
                //                                  REST API의 stateless(무속성) 특성 때문에 사용자에 대한 정보가 서버에 저장되지 않기 때문이다.
                .csrf(AbstractHttpConfigurer::disable)
                // 인가 처리
                // 요청주소(엔드포인트)에 대한 권한 처리
                .authorizeHttpRequests((authorizeRequests) -> {
                    // static resorces에 대한 요청은 security에서 무시
                    authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    // /요청은 모든 사용자가 접속 가능하도록 설정
                    authorizeRequests.requestMatchers("/").permitAll();
                    // members의 모든 하위요청은 모든 사용자가 접속 가능하도록 설정
                    authorizeRequests.requestMatchers("/member/**").permitAll();
                    // boards의 모든 하위 요청은 ROLE_USER, ROLE_ADMIN만 접근할 수 있도록 설정
                    authorizeRequests.requestMatchers("/boards/**").hasAnyRole("USER", "ADMIN");
                    // 위에 설정한 요청주소를 제외한 모든 요청은 인증(로그인, 권한이 있는)된 사용자만 접근할 수 있도록 설정
                    authorizeRequests.anyRequest().authenticated();
                }).build();
    }

}
