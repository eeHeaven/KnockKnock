package jpaproject.knockknock.domain.message;

import jpaproject.knockknock.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="userchatroom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userchatroom_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String partenerId;
    private String partenerNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatroom_id")
    private ChatRoom chatRoom;

    private int unreadcount;

    private void setChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        chatRoom.userChatRooms.add(this);
    }
    private void setMember(Member member){
        this.member = member;
        member.getChatRooms().add(this);
    }

    public static UserChatRoom create(Member member, Member partner,ChatRoom chatRoom, boolean send) {
        UserChatRoom userChatRoom = new UserChatRoom();
        userChatRoom.setMember(member);
        userChatRoom.setChatRoom(chatRoom);
        userChatRoom.setUnreadcountBySending(send);
        userChatRoom.partenerId = partner.getUserId();
        userChatRoom.partenerNickname = partner.getNickName();
        return userChatRoom;
    }

    private void setUnreadcountBySending(boolean send){
        if(send) this.unreadcount = 0;
        else this.unreadcount++;
    }

}
