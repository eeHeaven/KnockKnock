package jpaproject.knockknock.domain.message;

import jpaproject.knockknock.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="userchatroom")
@Getter
@NoArgsConstructor
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

    public UserChatRoom(Member member, Member partner,ChatRoom chatRoom, boolean send) {
        this.member = member;
        this.chatRoom = chatRoom;
        chatRoom.userChatRooms.add(this);
        initUnreadcountBySending(send);
        this.partenerId = partner.getUserId();
        this.partenerNickname = partner.getNickName();
    }
    public void initUnreadcountBySending(boolean send){
        if(send) this.unreadcount = 0;
        else this.unreadcount = 1;
    }
}
