package com.example.bcsd.controller;

import com.example.bcsd.dto.MemberDto;
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
    public ResponseEntity<MemberDto> getById(@PathVariable int id) {
        Member m = memberService.getMemberById(id);
        return ResponseEntity.ok(toDto(m));
    }

    @PostMapping
    public ResponseEntity<MemberDto> create(@RequestBody MemberDto dto) {
        Member toSave = new Member(0, dto.getName(), dto.getEmail(), dto.getPassword());
        Member created = memberService.createMember(toSave);
        return ResponseEntity
                .created(URI.create("/members/" + created.getMemberId()))
                .body(toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable int id, @RequestBody MemberDto dto) {
        Member toUpdate = new Member(id, dto.getName(), dto.getEmail(), dto.getPassword());
        Member updated = memberService.updateMember(id, toUpdate);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    private MemberDto toDto(Member m) {
        return new MemberDto((long) m.getMemberId(), m.getName(), m.getEmail(), m.getPassword());
    }

}
