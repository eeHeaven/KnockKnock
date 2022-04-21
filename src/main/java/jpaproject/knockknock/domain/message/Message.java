package jpaproject.knockknock.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="message")
@Getter
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private Long id;

    private String message;
    private String senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Column(name="message_timedate")
    private String timestamp;

    protected Message() {
    }

    @PrePersist
    public void onPrePersist(){
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.timestamp = s;
    }

    public Message(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
