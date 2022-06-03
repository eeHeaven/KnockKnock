package jpaproject.knockknock.api.response;

import jpaproject.knockknock.api.response.MessageResponse;
import jpaproject.knockknock.domain.message.UserChatRoom;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserChatRoomResponse {

    private String partnerNickname;
    private String partnerid;
    private List<MessageResponse> messages;

    public static UserChatRoomResponse entityToDto(UserChatRoom userChatRoom){
        return builder()
                .messages(MessageResponse.entityListToDtoList(userChatRoom.getChatRoom().getMessages()))
                .partnerid(userChatRoom.getPartenerId())
                .partnerNickname(userChatRoom.getPartenerNickname())
                .build();
    }
}
