package jpaproject.knockknock.api;

import com.sun.istack.NotNull;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.requestForm.LoginInfo;
import jpaproject.knockknock.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping(value = "api/member")
    public MemberBasicInfo signup(@RequestBody @Valid SignInForm form){
        Member newMember = new Member(form.getNickname(),form.getMemberId(),form.getMemberPassword());
        memberService.signUp(newMember);
        MemberBasicInfo response = new MemberBasicInfo(newMember.getNickName(),newMember.getUserId(),newMember.getSharePoint());
        return response;
    }

    @Data
    @AllArgsConstructor
     static class MemberBasicInfo{
        String nickname;
        String memberId;
        int point;
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
    @GetMapping("api/member/{userid}")
    public MemberBasicInfo viewAllMember(@PathVariable("userid") String id){
        Member member = memberService.findByUserId(id);
        MemberBasicInfo memberResponse = new MemberBasicInfo(member.getNickName(),member.getUserId(),member.getSharePoint());
        return memberResponse;
    }

    @GetMapping("api/login/{id}/{pw}")
    public MemberBasicInfo login(@PathVariable("id") String id, @PathVariable("pw") String pw){
        Member member = memberService.login(new LoginInfo(id,pw));
        MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getNickName(),member.getUserId(),member.getSharePoint());
        return memberBasicInfo;
    }

    @PutMapping("api/member/sharePoint/{id}")
    public MemberBasicInfo sharePoint(@PathVariable("id") String id, @FieldValue("point") int point){
       Member member = memberService.changeSharePoint(id,point);
       MemberBasicInfo response = new MemberBasicInfo(member.getNickName(), member.getUserId(),member.getSharePoint());
       log.info("response = {}",response);
       return response;
    }
    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


}
