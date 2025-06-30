package com.example.bcsd.service;

import com.example.bcsd.model.Member;
import com.example.bcsd.requestDto.MemberRequestDto;
import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.DuplicateEmailException;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepo;
    private final ArticleRepository articleRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<Member> getMemberById(Long id) {
        return memberRepo.findById(id);
    }

    @Transactional
    public Member createMember(MemberRequestDto req) {
        if (req.getName() == null || req.getEmail() == null || req.getPassword() == null) {
            throw new BadRequestException("요청값이 누락되었습니다.");
        }
        if (memberRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateEmailException("이메일이 이미 존재함: " + req.getEmail());
        }
        String encoded = passwordEncoder.encode(req.getPassword());
        Member m = new Member(req.getName(), req.getEmail(), encoded);
        return memberRepo.save(m);
    }

    @Transactional
    public Optional<Member> updateMember(Long id, MemberRequestDto req) {
        return memberRepo.findById(id)
                .map(m -> {
                    if (req.getName() == null || req.getEmail() == null) {
                        throw new BadRequestException("요청값이 null이거나 미요청한 값이 있음");
                    }
                    memberRepo.findByEmail(req.getEmail())
                            .filter(other -> !other.getMemberId().equals(id))
                            .ifPresent(other -> {
                                throw new DuplicateEmailException("이메일이 이미 존재함: " + req.getEmail());
                            });
                    m.setName(req.getName());
                    m.setEmail(req.getEmail());
                    if (req.getPassword() != null) {
                        m.setPassword(passwordEncoder.encode(req.getPassword()));
                    }
                    return m;
                });
    }

    @Transactional
    public boolean deleteMember(Long id) {
        return memberRepo.findById(id)
                .map(m -> {if (!articleRepo.findByAuthorId(id).isEmpty()) {
                    throw new BadRequestException("회원 " + id + "을 지울 수 없음, 게시글이 존재함");
                    }
                    memberRepo.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
