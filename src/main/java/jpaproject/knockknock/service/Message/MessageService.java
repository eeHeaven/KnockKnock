package jpaproject.knockknock.service.Message;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public Message sendMessage(Member sender,String messagetext){
        Message message = new Message(sender.getUserId(),messagetext);
       return messageRepository.save(message);
    }

    @Transactional
    public Message sendFirstMessage(Member sender, Member receiver, String message){
        Message firstMessage = sendMessage(sender,message);
        chatRoomService.makeChatRoom(sender,receiver,firstMessage);
        return firstMessage;
    }
}
