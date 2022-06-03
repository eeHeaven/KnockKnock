package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.Member;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "memberInfoResponseBuilder")
public class MemberInfoResponse {
    private String nickname;
    private String memberId;
    private int point;

    public static MemberInfoResponse entityToDto(Member member){
        return memberInfoResponseBuilder()
                .memberId(member.getUserId())
                .nickname(member.getNickName())
                .point(member.getSharePoint())
                .build();
    }
}
