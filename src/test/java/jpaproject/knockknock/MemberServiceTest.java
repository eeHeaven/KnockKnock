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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
        Member member = new Member("테스트5","testmember5","1234");
        Member member2 = new Member("테스트6","testmember6","1234");
        memberService.signUp(member);
        memberService.signUp(member2);
    }

    @Test
    public void 회원가입(){
        //given
        Member newmember = new Member("테스트7","newmember7","1234");

        //when
        Member member = memberService.signUp(newmember);
        //then
        Member member1 = memberRepository.findByUserId("newmember7");
        Member member2 = memberRepository.findOne(member.getId());
        Assertions.assertThat(member1).isEqualTo(member2);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원가입안됨(){
        //given
        Member newmember = new Member("테스트","testmember1","1234");
        //when
        Member member = memberService.signUp(newmember);
        //then
        Assertions.fail("예외 발생 안됨");
    }

    @Test
    public void 로그인(){
        //given
        LoginInfo loginInfo = new LoginInfo("testmember1","1234");
        //when
        Member member = memberService.login(loginInfo);
        //then
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getUserId()).isEqualTo("testmember1");
    }

    @Test(expected = IllegalStateException.class)
    public void 로그인실패(){
        //given
        LoginInfo loginInfo = new LoginInfo("testmember1","5678");
        //when
        Member member = memberService.login(loginInfo);
        //then
        Assertions.fail("예외 발생 안됨");
    }
}
