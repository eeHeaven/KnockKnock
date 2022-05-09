package jpaproject.knockknock.service.Message;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.ChatRoom;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.message.ChatRoomRepository;
import jpaproject.knockknock.repository.message.MessageRepository;
import jpaproject.knockknock.repository.message.UserChatRoomRepository;
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
    private final MemberRepository memberRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Transactional
    public Message save(Member sender, String messagetext){
        Message message = new Message(sender.getUserId(),messagetext);
       return messageRepository.save(message);
    }

    @Transactional
    public Message sendFirstMessage(String senderid, String receiverid, String message){
        Member sender = memberRepository.findByUserId(senderid);
        Message messageToSend = save(sender,message);
        ChatRoom chatRoom = chatRoomService.chatRoomExistBetween2Members(sender,receiverid);
        if(chatRoom==null){
            Member receiver = memberRepository.findByUserId(receiverid);
            chatRoomService.makeChatRoom(sender,receiver,messageToSend);
        }
        else {
            chatRoom.addMessage(messageToSend);
        }
        return messageToSend;
    }

    @Transactional
    public UserChatRoom sendMessageAtChatRoom(Long userChatRoomid, String message, Member sender){
        Message messageToSend = save(sender,message);
        UserChatRoom userChatRoom = userChatRoomRepository.getById(userChatRoomid);
        ChatRoom chatRoom = userChatRoom.getChatRoom();
        chatRoom.addMessage(messageToSend);
        return userChatRoom;
    }
}
