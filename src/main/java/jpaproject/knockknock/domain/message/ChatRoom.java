package jpaproject.knockknock.domain.message;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="chatroom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatroom_id")
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    List<Message> messages = new LinkedList<>();

    String lastMessage;
    String lastMessageTimeStamp;

    public static ChatRoom createWithFirstMessage(Message message){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addMessage(message);
        return chatRoom;
    }

    public Message addMessage(Message message){
        messages.add(0,message);
        message.setChatRoom(this);
        this.lastMessage = message.getMessage();
        this.lastMessageTimeStamp = message.getTimestamp();

        return message;
    }

}
