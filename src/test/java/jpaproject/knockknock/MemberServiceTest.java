package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.requestForm.LoginInfo;
import jpaproject.knockknock.requestForm.SignInRequest;
import jpaproject.knockknock.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void 초기데이터설정(){
        SignInRequest signInRequest1 = new SignInRequest();
        signInRequest1.setId("testmember1");
        signInRequest1.setPassword("1234");
        signInRequest1.setNickname("테스트1");
        Member member1 = memberService.signIn(signInRequest1);

        SignInRequest signInRequest2 = new SignInRequest();
        signInRequest2.setId("testmember2");
        signInRequest2.setPassword("1234");
        signInRequest2.setNickname("테스트2");
        Member member2 = memberService.signIn(signInRequest2);
    }

    @Test
    public void 회원가입(){
        //given
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setId("newmember1");
        signInRequest.setPassword("1234");
        signInRequest.setNickname("테스트");

        //when
        Member member = memberService.signIn(signInRequest);
        //then
        Member member1 = memberRepository.findByUserId("newmember1");
        Member member2 = memberRepository.findOne(member.getId());
        Assertions.assertThat(member1).isEqualTo(member2);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원가입안됨(){
        //given
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setId("testmember1");
        signInRequest.setPassword("1234");
        signInRequest.setNickname("테스트");
        //when
        Member member = memberService.signIn(signInRequest);
        //then
        Assertions.fail("예외 발생 안됨");
    }

    @Test
    public void 로그인(){
        //given
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId("testmember1");
        loginInfo.setPassword("1234");
        //when
        Member member = memberService.login(loginInfo);
        //then
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getUserId()).isEqualTo("testmember1");
    }

    @Test(expected = IllegalStateException.class)
    public void 로그인실패(){
        //given
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId("testmember1");
        loginInfo.setPassword("5678");
        //when
        Member member = memberService.login(loginInfo);
        //then
        Assertions.fail("예외 발생 안됨");
    }
}
