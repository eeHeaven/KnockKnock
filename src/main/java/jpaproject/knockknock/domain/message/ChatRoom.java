package jpaproject.knockknock.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="chatroom")
@Getter
@NoArgsConstructor
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

    public ChatRoom(Message message){
        addMessage(message);
    }

    public Message addMessage(Message message){
        messages.add(0,message);
        message.setChatRoom(this);
        this.lastMessage = message.getMessage();
        this.lastMessageTimeStamp = message.getTimestamp();

        return message;
    }
}
