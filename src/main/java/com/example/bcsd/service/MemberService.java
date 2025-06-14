package com.example.bcsd.service;

import com.example.bcsd.requestDto.MemberRequestDto;
import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.DuplicateEmailException;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepo;
    private final ArticleRepository articleRepo;

    public MemberService(MemberRepository memberRepo,
                         ArticleRepository articleRepo) {
        this.memberRepo = memberRepo;
        this.articleRepo = articleRepo;
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMemberById(Long id) {
        return memberRepo.findById(id.intValue());
    }

    @Transactional
    public Member createMember(MemberRequestDto req) {
        if (req.getName() == null || req.getEmail() == null || req.getPassword() == null) {
            throw new BadRequestException("요청값이 null이거나 미요청한 값이 있음");
        }
        if (memberRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateEmailException("이메일이 이미 존재함: " + req.getEmail());
        }
        Member m = new Member();
        m.setName(req.getName());
        m.setEmail(req.getEmail());
        m.setPassword(req.getPassword());
        return memberRepo.save(m);
    }

    @Transactional
    public Optional<Member> updateMember(Long id, MemberRequestDto req) {
        Optional<Member> opt = getMemberById(id);
        if (opt.isEmpty()) return Optional.empty();

        if (req.getName() == null || req.getEmail() == null) {
            throw new BadRequestException("요청값이 Null임");
        }
        Optional<Member> byEmail = memberRepo.findByEmail(req.getEmail());
        if (byEmail.isPresent() && !byEmail.get().getMemberId().equals(id.intValue())) {
            throw new DuplicateEmailException("이메일이 이미 사용 중임 " + req.getEmail());
        }
        Member m = opt.get();
        m.setName(req.getName());
        m.setEmail(req.getEmail());
        m.setPassword(req.getPassword());
        return Optional.of(memberRepo.update(id.intValue(), m));
    }

    @Transactional
    public boolean deleteMember(Long id) {
        Optional<Member> opt = getMemberById(id);
        if (opt.isEmpty()) return false;
        if (!articleRepo.findByBoardId(id).isEmpty()) {
            throw new BadRequestException("회원 " + id + "을 지울 수 없음, 게시글이 존재함");
        }
        memberRepo.deleteById(id.intValue());
        return true;
    }
}
