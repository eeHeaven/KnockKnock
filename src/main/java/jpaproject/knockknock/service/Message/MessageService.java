package jpaproject.knockknock.service.Message;

import jpaproject.knockknock.api.request.MessageRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.ChatRoom;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.message.ChatRoomRepository;
import jpaproject.knockknock.repository.message.MessageRepository;
import jpaproject.knockknock.repository.message.UserChatRoomRepository;
import jpaproject.knockknock.strategy.factory.PointModifyFactory;
import jpaproject.knockknock.strategy.pointmodify.PointModify;
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
    private final PointModifyFactory pointModifyFactory;

    @Transactional
    public Message save(MessageRequest request){
        Message message = Message.dtoToEntity(request);
       return messageRepository.save(message);
    }

    @Transactional
    public Message sendMessage(MessageRequest request){
        Message messageToSend = save(request);
        Member receiver = memberRepository.findByUserId(request.getReceiverId())
                .orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));
        Member sender = memberRepository.findByUserId(request.getSenderId())
                .orElseThrow(()->new CustomException(ExceptionEnum.USER_NOT_FOUND));

        //chatroom이 없다면 생성, 있다면 가져온 후 메세지 추가
        ChatRoom chatRoom = chatRoomService.chatRoomBetween2Members(sender, receiver);
        if(chatRoom==null){
            chatRoomService.makeChatRoom(sender,receiver,messageToSend);
        }
        else {
            chatRoom.addMessage(messageToSend);
        }
        //포인트변화
        PointModify sendpointModify = pointModifyFactory.findPointModify(PointModify.Situation.sendMessage);
        sendpointModify.modifyPointof(sender);
        PointModify receivepointModify = pointModifyFactory.findPointModify(PointModify.Situation.receiveMessage);
        receivepointModify.modifyPointof(receiver);

        return messageToSend;
    }
}
