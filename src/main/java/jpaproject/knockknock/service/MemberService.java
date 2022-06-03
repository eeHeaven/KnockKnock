package jpaproject.knockknock.service;

import jpaproject.knockknock.api.request.SignUpRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.api.request.LoginRequest;
import jpaproject.knockknock.strategy.pointmodify.PointModify;
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
    public Member signUp(SignUpRequest request){
        Member member = Member.dtoToEntity(request);
        validateSameIdExsist(request.getMemberId());
        validateSameNickNameExsist(request.getNickname());

        memberRepository.save(member);
        return member;
    }

    //회원가입시 동일한 id의 회원이 있는지 검증
    private void validateSameIdExsist(String id){
        if(memberRepository.findByUserId(id).isPresent()){
            throw new CustomException(ExceptionEnum.ID_ALREADY_EXIST);
        } else {
            log.info("기존에 없는 아이디입니다. 회원가입 요청 id={}", id);
        }
    }
    private void validateSameNickNameExsist(String nickname){
        if(memberRepository.findByNickName(nickname).isPresent()){
            throw new CustomException(ExceptionEnum.NICKNAME_ALREADY_EXIST);
        }
        else{
            log.info("기존에 없는 닉네임입니다. 회원가입 요청 nickname ={}",nickname);
        }
    }

    public Member login(LoginRequest loginRequest){
        String userId = loginRequest.getId();
        String userPassword = loginRequest.getPassword();
        Member loginMember = memberRepository.findByUserIdAndUserPassword(userId,userPassword)
                .orElseThrow(()->new CustomException(ExceptionEnum.LOGIN_FAIL));
        return loginMember;
    }

    public Member findByUserId(String id){
        return memberRepository.findByUserId(id)
                .orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));
    }

    // 멤버의 포인트 변화
    @Transactional
    public Member modifyPoint(String id, PointModify pointModify){
        Member member = memberRepository.findByUserId(id)
                .orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));
        return pointModify.modifyPointof(member);
    }


}
