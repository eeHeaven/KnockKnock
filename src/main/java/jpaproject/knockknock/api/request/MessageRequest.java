package jpaproject.knockknock.api.request;

import jpaproject.knockknock.domain.Location;
import lombok.Data;


@Data
public class MessageRequest {
    String senderId;
    String receiverId;
    String message;

}
