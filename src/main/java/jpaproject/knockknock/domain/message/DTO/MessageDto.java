package jpaproject.knockknock.domain.message.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto {

    private String senderId;
    private String message;
    private String timestamp;

}
