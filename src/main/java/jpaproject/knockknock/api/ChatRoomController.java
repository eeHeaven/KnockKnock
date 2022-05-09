package jpaproject.knockknock.api;

import jpaproject.knockknock.domain.message.DTO.ChatRoomListDto;
import jpaproject.knockknock.domain.message.DTO.MessageDto;
import jpaproject.knockknock.domain.message.DTO.UserChatRoomDto;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.repository.message.ChatRoomRepository;
import jpaproject.knockknock.repository.message.UserChatRoomRepository;
import jpaproject.knockknock.service.Message.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserChatRoomRepository userChatRoomRepository;

    @GetMapping("api/userchatroom/list/{userId}")
    public Result viewUserChatRoomList(@PathVariable("userId") String userId){
        List<UserChatRoom> chatRooms = userChatRoomRepository.findByMemberId(userId);
        List<ChatRoomListDto> dtos = chatRooms.stream().map(c-> new ChatRoomListDto(
                c.getId(),c.getChatRoom().getLastMessage(),c.getPartenerNickname(),c.getChatRoom().getLastMessageTimeStamp()))
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    @GetMapping("api/userchatroom/{chatroomid}")
    public UserChatRoomDto viewUserChatRoom(@PathVariable("chatroomid")Long id){
        UserChatRoom userChatRoom = userChatRoomRepository.getById(id);
        List<Message> messages = userChatRoom.getChatRoom().getMessages();
        List<MessageDto> messageDtos = messages.stream().map(m->new MessageDto(m.getSenderId(),m.getMessage(),m.getTimestamp())).collect(Collectors.toList());
        return new UserChatRoomDto(userChatRoom.getPartenerNickname(),userChatRoom.getPartenerId(),messageDtos );
    }

}

