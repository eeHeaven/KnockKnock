package jpaproject.knockknock;

import jpaproject.knockknock.api.request.SignUpRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.api.request.LoginRequest;
import jpaproject.knockknock.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Before
    public void 초기데이터설정(){
        Member member =Member.memberBuilder().userId("testmember5").nickName("테스트5").userPassword("1234").build();
        Member member2 = Member.memberBuilder().userId("testmember6").nickName("테스트6").userPassword("1234").build();
        memberRepository.save(member);
        memberRepository.save(member2);
    }

    @Test
    public void 회원가입(){
        //given
        SignUpRequest request = new SignUpRequest("테스트멤버7","testmember7","1234");

        //when
        Member member = memberService.signUp(request);
        //then
        Member member1 = memberRepository.findByUserId("newmember7").orElseThrow(()->new IllegalArgumentException("해당 멤버 없음"));
        Member member2 = memberRepository.findById(member.getId()).orElseThrow(()-> new IllegalArgumentException("해당 멤버 없음"));
        Assertions.assertThat(member1).isEqualTo(member2);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원가입안됨(){
        //given
        SignUpRequest request = new SignUpRequest("테스트","testmember1","1234");
        //when
        Member member = memberService.signUp(request);
        //then
        Assertions.fail("예외 발생 안됨");
    }

    @Test
    public void 로그인(){
        //given
        LoginRequest loginRequest = new LoginRequest("testmember1","1234");
        //when
        Member member = memberService.login(loginRequest);
        //then
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getUserId()).isEqualTo("testmember1");
    }

    @Test(expected = IllegalStateException.class)
    public void 로그인실패(){
        //given
        LoginRequest loginRequest = new LoginRequest("testmember1","5678");
        //when
        Member member = memberService.login(loginRequest);
        //then
        Assertions.fail("예외 발생 안됨");
    }
}
