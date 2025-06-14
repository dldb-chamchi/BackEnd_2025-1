package com.example.bcsd.controller;

import com.example.bcsd.requestDto.MemberRequestDto;
import com.example.bcsd.responseDto.MemberResponseDto;
import com.example.bcsd.model.Member;
import com.example.bcsd.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getById(@PathVariable("id") Long id) {
        return memberService.getMemberById(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> create(@RequestBody MemberRequestDto req) {
        Member created = memberService.createMember(req);
        MemberResponseDto dto = toResponse(created);
        return ResponseEntity
                .created(URI.create("/members/" + dto.getId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody MemberRequestDto req
    ) {
        return memberService.updateMember(id, req)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (memberService.deleteMember(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MemberResponseDto toResponse(Member m) {
        return new MemberResponseDto(
                m.getMemberId().longValue(),
                m.getName(),
                m.getEmail(),
                m.getPassword()
        );
    }
}
