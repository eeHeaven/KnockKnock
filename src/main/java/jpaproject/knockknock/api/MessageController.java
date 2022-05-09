package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.MessageRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.DTO.MessageDto;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.service.MemberService;
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
    private final MemberService memberService;

    @PostMapping("api/message/send")
    public MessageDto sendMessage(@RequestBody @Valid MessageRequest request){
        Member sender = memberService.findByUserId(request.getSenderId());
        Member receiver = memberService.findByUserId(request.getReceiverId());
        Message message = messageService.sendFirstMessage(request.getSenderId(),request.getReceiverId(), request.getMessage());
        return new MessageDto(message.getSenderId(), message.getMessage(), message.getTimestamp());
    }
}
