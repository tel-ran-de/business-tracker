package de.telran.businesstracker.controller;

import de.telran.businesstracker.data.Member;
import de.telran.businesstracker.dto.MemberDto;
import de.telran.businesstracker.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@Transactional
public class MemberController {

    MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Member> createMember(@RequestBody @Valid MemberDto memberDto) throws URISyntaxException {
        Member result = memberService.add(memberDto.position,memberDto.projectId, memberDto.userId);
        return ResponseEntity
            .created(new URI("/api/members/" + result.getId()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable(value = "id", required = false)
        @RequestBody @Valid MemberDto memberDto) throws HttpClientErrorException.BadRequest {
        Member member = memberService.getById(memberDto.id);

        Member result = memberService.edit(member, memberDto.position,memberDto.projectId, memberDto.userId);

        return ResponseEntity
            .ok()
            .body(result);
    }

    @GetMapping("")
    public List<Member> getAllMembers() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        Member member = memberService.getById(id);
        return ResponseEntity.ok().body(member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.removeById(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
