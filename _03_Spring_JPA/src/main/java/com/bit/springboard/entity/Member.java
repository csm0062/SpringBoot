package com.bit.springboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
// @Table을 Drop하면 자동으로 class명으로 테이블이 생성됨
// @Table(name = "T_MEMBER")
@Getter
@Setter
// @DynamicInsert null 값이 입력되면 Default값으로 지정해주는 어노테이션
@DynamicInsert
public class Member {
    // @Id: private Long id => 이걸 PrimaryKey로 지정하는 어노테이션
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 300)
    private String password;

    @Column(unique = true)
    private String nickname;

    private String email;

    private String tel;

    @ColumnDefault("'ROLE_USER'")
    private String role;

    // @Transient: 테이블에서 생성되지 않고 java에서만 사용할 변수를 선언하는 어노테이션
//    @Transient
//    private LocalDateTime regdate;

}
