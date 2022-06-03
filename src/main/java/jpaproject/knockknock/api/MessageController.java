package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.MessageRequest;
import jpaproject.knockknock.api.response.MessageResponse;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.service.Message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("api/message/send")
    public MessageResponse sendMessage(@RequestBody @Valid MessageRequest request){
        Message message = messageService.sendMessage(request);
        return MessageResponse.entityToDto(message);
    }
}
