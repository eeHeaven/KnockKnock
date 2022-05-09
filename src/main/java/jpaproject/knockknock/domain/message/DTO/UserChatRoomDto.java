package jpaproject.knockknock.domain.message.DTO;

import jpaproject.knockknock.domain.message.DTO.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserChatRoomDto {

    String partnerNickname;
    String partnerid;
    List<MessageDto> messages;
}
