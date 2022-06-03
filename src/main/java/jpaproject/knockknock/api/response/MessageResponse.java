package jpaproject.knockknock.api.response;

import jpaproject.knockknock.domain.message.Message;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MessageResponse {

    private String senderId;
    private String message;
    private String timestamp;

    public static MessageResponse entityToDto(Message message){
        return builder()
                .message(message.getMessage())
                .senderId(message.getSenderId())
                .timestamp(message.getTimestamp())
                .build();
    }
    public static List<MessageResponse> entityListToDtoList(List<Message> messageList){
        return messageList.stream().map(m-> MessageResponse.entityToDto(m)).collect(Collectors.toList());
    }

}
