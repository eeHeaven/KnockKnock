package jpaproject.knockknock.domain.message;

import jpaproject.knockknock.api.request.MessageRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="message")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

    @PrePersist
    public void onPrePersist(){
        String s = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        this.timestamp = s;
    }

    public static Message dtoToEntity(MessageRequest request){
        return builder()
                .message(request.getMessage())
                .senderId(request.getSenderId())
                .build();
    }

     void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
