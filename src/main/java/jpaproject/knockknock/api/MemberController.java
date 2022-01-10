package jpaproject.knockknock.api;

import com.sun.istack.NotNull;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원가입
    @PostMapping("api/member")
    public MemberBasicInfo signin(@RequestBody @Valid SignInForm form){
        Member newMember = new Member(form.getNickname(),form.getMemberId(),form.getMemberPassword());
        memberService.signIn(newMember);
        MemberBasicInfo response = new MemberBasicInfo(newMember.getNickName(),newMember.getUserId());
        return response;
    }

    @Data
    @AllArgsConstructor
     static class MemberBasicInfo{
        String nickname;
        String memberId;
        public MemberBasicInfo(){}

    }

    @Data
    @AllArgsConstructor
    static class SignInForm{
        @NotNull
        String nickname;
        @NotNull
        String memberId;
        @NotNull
        String memberPassword;
        public SignInForm(){}
    }

    //회원 정보 조회(아이디를 통해)
    @GetMapping("api/member/{id}")
    public MemberBasicInfo viewAllMember(@PathVariable("id") Long id){
        Member member = memberRepository.findOne(id);
        MemberBasicInfo memberResponse = new MemberBasicInfo(member.getNickName(),member.getUserId());
        return memberResponse;
    }
    
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


}
