package com.example.bcsd.repository;

import com.example.bcsd.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //이메일로 조회
    Optional<Member> findByEmail(String email);

    //이메일 중복 체크
    boolean existsByEmail(String email);
}
