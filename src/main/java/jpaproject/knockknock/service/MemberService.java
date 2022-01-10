package jpaproject.knockknock.service;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.requestForm.LoginInfo;
import jpaproject.knockknock.requestForm.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional(readOnly = false)
    public Member signIn(Member member){
        String userId = member.getUserId();
        validateSameIdExsist(userId);
        String userPassword = member.getUserPassword();
        String nickname = member.getNickName();
        validateSameNickNameExsist(nickname);

        memberRepository.save(member);
        return member;
    }

    //회원가입시 동일한 id의 회원이 있는지 검증
    private void validateSameIdExsist(String id){
        if(memberRepository.findByUserId(id)!= null){
            throw new IllegalStateException("이미 존재하는 id입니다.");
        }
    }
    private void validateSameNickNameExsist(String nickname){
        if(memberRepository.findByNickName(nickname)!= null){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    public Member login(LoginInfo loginInfo){
        String userId = loginInfo.getId();
        String userPassword = loginInfo.getPassword();
        Member loginMember = memberRepository.LoginMemberReturn(userId,userPassword);
        if(loginMember == null){
            throw new IllegalStateException("id나 password를 다시 입력하세요.");
        }
        return loginMember;
    }


}
