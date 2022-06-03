package jpaproject.knockknock.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketMessageController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/send")
    public void message(SocketMessage message){
        simpMessageSendingOperations.convertAndSend("/sub/channel/"+message.getChannelId(),message);
    }
}
