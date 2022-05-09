package jpaproject.knockknock.domain.message.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomListDto {

    Long id;
    String lastMessage;
    String partnerNickname;
    String lastMessageTimeStamp;

}
