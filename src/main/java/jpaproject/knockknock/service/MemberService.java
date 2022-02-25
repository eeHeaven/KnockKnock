package jpaproject.knockknock.service;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.exception.LoginUnableException;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.requestForm.LoginInfo;
import jpaproject.knockknock.requestForm.SignInRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional(readOnly = false)
    public Member signUp(Member member){
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
            log.info("이미 존재하는 id입니다. 회원가입 요청 id={}", id);
            throw new IllegalStateException("이미 존재하는 id입니다ㅠㅠ 다른 id를 사용하세요.");
        } else {
            log.info("기존에 없는 아이디입니다. 회원가입 요청 id={}", id);
        }
    }
    private void validateSameNickNameExsist(String nickname){
        if(memberRepository.findByNickName(nickname) != null){
            throw new IllegalStateException("이미 존재하는 닉네임입니다ㅠㅠ 다른 닉네임을 사용하세요.");
        }
    }

    public Member login(LoginInfo loginInfo){
        String userId = loginInfo.getId();
        String userPassword = loginInfo.getPassword();
        Member loginMember = memberRepository.LoginMemberReturn(userId,userPassword);
        if(loginMember == null){
            throw new LoginUnableException(ExceptionEnum.LOGIN_UNABLE);
        }
        return loginMember;
    }

    public Member findByUserId(String id){
        return memberRepository.findByUserId(id);
    }

    // 포인트 변화
    @Transactional
    public Member changeSharePoint(String id, int point){
        Member member = memberRepository.findByUserId(id);
        int memberpoint = member.getSharePoint();
        member.setSharePoint(memberpoint+point);
        return member;
    }


}
