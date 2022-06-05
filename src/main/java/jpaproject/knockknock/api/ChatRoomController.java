package jpaproject.knockknock.api;

import jpaproject.knockknock.api.response.ChatRoomListResponse;
import jpaproject.knockknock.api.response.UserChatRoomResponse;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.UserChatRoom;
import jpaproject.knockknock.exception.ExceptionEnum;
import jpaproject.knockknock.exception.CustomException;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.message.UserChatRoomRepository;
import jpaproject.knockknock.service.Message.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserChatRoomRepository userChatRoomRepository;

    @GetMapping("api/userchatroom/list/{userId}")
    public Result<List<ChatRoomListResponse>> viewUserChatRoomList(@PathVariable("userId") String userId) {
        List<UserChatRoom> chatRooms = chatRoomService.viewChatRoom(userId);
        List<ChatRoomListResponse> dtos = chatRooms.stream()
                .map(ChatRoomListResponse::entityToDto)
                .collect(Collectors.toList());
        return new Result<>(SuccessCode.LIST_SUCCESSFULLY_RETURNED, dtos);
    }

    @GetMapping("api/userchatroom/{chatroomid}")
    public UserChatRoomResponse viewUserChatRoom(@PathVariable("chatroomid") Long id) {
        UserChatRoom userChatRoom = userChatRoomRepository.getById(id);
        return UserChatRoomResponse.entityToDto(userChatRoom);
    }

}

