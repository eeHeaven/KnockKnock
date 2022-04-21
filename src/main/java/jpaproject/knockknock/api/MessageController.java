package jpaproject.knockknock.api;

import jpaproject.knockknock.api.request.FirstMessageRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.DTO.MessageDTO;
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

    @PostMapping("api/sendfirst")
    public MessageDTO sendfirstMessage(@RequestBody @Valid FirstMessageRequest request){
        // target Location 인근 receiver 찾기
        Member sender = memberService.findByUserId(request.getSenderId());
        //TODO: 인근 receiver 찾는 코드 구현되면 이 부분은 수정하기
        Member receiver = memberService.findByUserId("receiver");
        Message firstMessage = messageService.sendFirstMessage(sender,receiver, request.getMessage());
        return new MessageDTO(firstMessage.getSenderId(), firstMessage.getMessage(), firstMessage.getTimestamp());
    }
}
