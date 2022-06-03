package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.message.UserChatRoom;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatRoomListResponse {

   private Long id;
   private String lastMessage;
   private String partnerNickname;
   private String lastMessageTimeStamp;

   public static ChatRoomListResponse entityToDto(UserChatRoom userChatRoom){
      return builder()
              .id(userChatRoom.getId())
              .partnerNickname(userChatRoom.getPartenerNickname())
              .lastMessage(userChatRoom.getChatRoom().getLastMessage())
              .lastMessageTimeStamp(userChatRoom.getChatRoom().getLastMessageTimeStamp())
              .build();
   }
}
