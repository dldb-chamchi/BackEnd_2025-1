package com.example.bcsd.service;

import com.example.bcsd.exception.BadRequestException;
import com.example.bcsd.exception.DuplicateEmailException;
import com.example.bcsd.exception.ResourceNotFoundException;
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

    public MemberService(MemberRepository memberRepo, ArticleRepository articleRepo) {
        this.memberRepo = memberRepo;
        this.articleRepo = articleRepo;
    }

    @Transactional(readOnly = true)
    public Member getMemberById(int id) {
        return memberRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("해당 회원을 찾을 수 없음 : " + id));
    }

    @Transactional
    public Member createMember(Member m) {
        if (m.getName()==null || m.getEmail()==null || m.getPassword()==null) {
            throw new BadRequestException("요청값이 null이거나 미요청한 값이 있음");
        }
        if (memberRepo.existsByEmail(m.getEmail())) {
            throw new DuplicateEmailException("이메일이 이미 존재함: " + m.getEmail());
        }
        return memberRepo.save(m);
    }

    @Transactional
    public Member updateMember(int id, Member m) {
        getMemberById(id);
        if (m.getName()==null || m.getEmail()==null) {
            throw new BadRequestException("요청값이 Null임");
        }
        Optional<Member> byEmail = memberRepo.findByEmail(m.getEmail());
        if (byEmail.isPresent() && byEmail.get().getMemberId() != id) {
            throw new DuplicateEmailException("이메일이 이미 사용 중임 " + m.getEmail());
        }
        return memberRepo.update(id, m);
    }

    @Transactional
    public void deleteMember(int id) {
        getMemberById(id);
        if (!articleRepo.findByBoardId(id).isEmpty()) {
            throw new BadRequestException("회원 " + id + "을 지울 수 없음, 게시글이 존재함");
        }
        memberRepo.deleteById(id);
    }

}
