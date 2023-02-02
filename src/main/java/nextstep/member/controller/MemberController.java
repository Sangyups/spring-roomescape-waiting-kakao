package nextstep.member.controller;

import auth.annotation.AuthRequired;
import nextstep.member.domain.Member;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.member.mapper.MemberMapper;
import nextstep.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberRequest memberRequest) {
        Long id = memberService.create(MemberMapper.INSTANCE.requestDtoToDomain(memberRequest));

        return ResponseEntity.created(URI.create("/members/" + id)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthRequired Member member) {
        MemberResponse memberResponse = memberService.findById(member.getId())
                .map(MemberMapper.INSTANCE::domainToResponseDto)
                .orElse(null);

        return ResponseEntity.ok(memberResponse);
    }
}
