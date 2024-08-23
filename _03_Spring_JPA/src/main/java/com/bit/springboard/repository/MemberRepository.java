package com.bit.springboard.repository;

import com.bit.springboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
//public interface MemberRepository extends JpaRepository<Member, Long> 제네릭안에는  <어떤 Entity를 사용할 것인지, 그 Entity의 Id 형태>
public interface MemberRepository extends JpaRepository<Member, Long> {

}
