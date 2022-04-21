package jpaproject.knockknock.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    List<Message> messages = new ArrayList<>();

    public ChatRoom(Message message){
        messages.add(message);
        message.setChatRoom(this);
    }
}
