package jpaproject.knockknock.domain.message;

import jpaproject.knockknock.domain.Member;
import lombok.Getter;
import lombok.Setter;

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

    private Long senderId;
    private Long receiverId;
    // entity 단위로 연관관계 매핑할 경우 이점 = 영속성 컨텍스트가 관리 해준다는 것
    // 영속성 컨텍스트의 이점은 dirty checking과 효율성( 1차 캐시에서 바로 데이터 로드)
    // 효율성은 모르겠지만 dirty checking은 이 필드에서 굳이 해줄 필요가 없음
    // 그래서 당장은 id단위로 조회하기로.. 성능 문제는 나중에 생각해보자

    //비즈니스 로직
    //1. 연관관계 관련 로직
    public void setSenderId(Member member){
        this.senderId = member.getId();
        member.getMessagesWithUser().add(this);
    }
    public void setReceiverId(Member member){
        this.receiverId = member.getId();
        member.getMessagesWithUser().add(this);
    }
    public void addMessage(UserMessage message){
        this.messageList.add(message);
        message.setMessageDialog(this);
    }

    //2. 생성로직
    public MessageDialog CreateMessageDialog(Member sender, Member receiver, UserMessage firstMessage){
        MessageDialog newDialog = new MessageDialog();
        newDialog.setSenderId(sender);
        newDialog.setReceiverId(receiver);
        newDialog.addMessage(firstMessage);
        return newDialog;
    }
}
