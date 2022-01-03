package jpaproject.knockknock.requestForm;

import lombok.Data;

@Data
public class MessageForm {

    String message;
    Long sender;
    Long receiver;

}
