package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.SignUpRequest;
import jpaproject.knockknock.api.response.MemberInfoResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.api.request.LoginRequest;
import jpaproject.knockknock.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping(value = "api/member")
    public MemberInfoResponse signup(@RequestBody @Valid SignUpRequest form){
        Member newMember = memberService.signUp(form);
        MemberInfoResponse response = MemberInfoResponse.entityToDto(newMember);
        return response;
    }

    @GetMapping("api/login/{id}/{pw}")
    public MemberInfoResponse login(@PathVariable("id") String id, @PathVariable("pw") String pw){
        Member member = memberService.login(new LoginRequest(id,pw));
        MemberInfoResponse memberBasicInfo = MemberInfoResponse.entityToDto(member);
        return memberBasicInfo;
    }




}
