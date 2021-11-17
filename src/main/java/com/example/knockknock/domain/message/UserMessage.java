package com.example.knockknock.domain.message;

import com.example.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class UserMessage {

    @Id @GeneratedValue
    @Column(name="message_id")
    private Long id;
    private String message;

    private Long senderId;
    private Long receiverId;
    // entity 단위로 연관관계 매핑할 경우 이점 = 영속성 컨텍스트가 관리 해준다는 것
    // 영속성 컨텍스트의 이점은 dirty checking과 효율성( 1차 캐시에서 바로 데이터 로드)
    // 효율성은 모르겠지만 dirty checking은 이 필드에서 굳이 해줄 필요가 없음
    // 그래서 당장은 id단위로 조회하기로.. 성능 문제는 나중에 생각해보자

    @Column(name="message_send_time")
    private LocalDateTime timestamp;

}
