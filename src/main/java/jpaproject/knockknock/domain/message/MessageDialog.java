package jpaproject.knockknock.domain.message;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
// 각 대화상대마다의 대화창(카톡 생각하면 됨)
public class MessageDialog {

    @Id @GeneratedValue
    @Column(name="messageDialog_id")
    private Long id;

    @OneToMany(mappedBy = "messageDialog")
    private List<UserMessage> messageList = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long partnerId;
    // entity 단위로 연관관계 매핑할 경우 이점 = 영속성 컨텍스트가 관리 해준다는 것
    // 영속성 컨텍스트의 이점은 dirty checking과 효율성( 1차 캐시에서 바로 데이터 로드)
    // 효율성은 모르겠지만 dirty checking은 이 필드에서 굳이 해줄 필요가 없음
    // 그래서 당장은 id단위로 조회하기로.. 성능 문제는 나중에 생각해보자

    //비즈니스 로직
    //1. 연관관계 관련 로직
    public void addMessage(UserMessage message){
        this.getMessageList().add(message);
        message.setMessageDialog(this);
    }

    public void setOwner(Member member){
        member.getMessagesWithUser().add(this);
        this.setMember(member);
    }


    //2. 생성로직
    public MessageDialog CreateMessageDialog( Member receiver, UserMessage firstMessage){
        MessageDialog newDialog = new MessageDialog();

        return newDialog;
    }

}
