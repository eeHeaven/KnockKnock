package jpaproject.knockknock.domain.message;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
//각 메세지 하나하나 단위
public class UserMessage {

    @Id @GeneratedValue
    @Column(name="message_id")
    private Long id;
    private String message;

    private Long senderId;
    private Long recieverId;

    @Column(name="message_timedate")
    private LocalDateTime timestamp;

      @ManyToOne(fetch=FetchType.LAZY)
      @JoinColumn(name ="messageDialog_id")
      private MessageDialog messageDialog;

      //비즈니스로직

    public void addMessageTo(MessageDialog messageDialog){
        messageDialog.getMessageList().add(this);
        this.setMessageDialog(messageDialog);
    }
    //생성로직
    public UserMessage writeNewMessage(String message){
        UserMessage userMessage = new UserMessage();
        userMessage.setMessage(message);
        LocalDateTime time = LocalDateTime.now();
        userMessage.setTimestamp(time);
        return userMessage;
    }
}
