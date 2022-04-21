package jpaproject.knockknock.service.Message;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.ChatRoom;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.repository.message.ChatRoomRepository;
import jpaproject.knockknock.repository.message.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Transactional
    public ChatRoom makeChatRoom(Member sender, Member reciever, Message firstMessage){
        ChatRoom chatRoom = new ChatRoom(firstMessage);
        chatRoomRepository.save(chatRoom);
        makeUserChatRoom(sender,reciever,chatRoom,true);
        makeUserChatRoom(reciever,sender,chatRoom,false);
        return chatRoom;
    }

    @Transactional
    public UserChatRoom makeUserChatRoom(Member member, Member partner,ChatRoom chatRoom, boolean send){
        UserChatRoom userChatRoom = new UserChatRoom(member,partner,chatRoom,send);
        return userChatRoomRepository.save(userChatRoom);
    }

    public List<ChatRoom> findAllChatRooms(){
        return chatRoomRepository.findAll();
    }
    public List<UserChatRoom> findAllUserChatRooms(){
        return userChatRoomRepository.findAll();
    }
}
