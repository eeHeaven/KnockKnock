package jpaproject.knockknock.service;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.MessageDialog;
import jpaproject.knockknock.domain.message.UserMessage;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.message.MessageDialogRepository;
import jpaproject.knockknock.repository.message.UserMessageRepository;
import jpaproject.knockknock.requestForm.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageDialogRepository messageDialogRepository;
    private final UserMessageRepository userMessageRepository;
    private final MemberRepository memberRepository;

    //메세지 보내기
    @Transactional
    public UserMessage sendMessage(MessageForm messageForm){

        //userMessage 설정
        UserMessage message = new UserMessage();
        message.setMessage(messageForm.getMessage());
        message.setSenderId(message.getSenderId());
        message.setRecieverId(message.getRecieverId());
        message.setTimestamp(LocalDateTime.now());
        userMessageRepository.save(message);

        Member sender = memberRepository.findOne(messageForm.getSender());
        Member receiver = memberRepository.findOne(messageForm.getReceiver());

        MessageDialog dialog1 = returnDialog(sender, receiver.getId());
        MessageDialog dialog2 = returnDialog(receiver, sender.getId());

        dialog1.addMessage(message);
        dialog2.addMessage(message);

        return message;
    }

    @Transactional
    public MessageDialog returnDialog(Member sender,Long receiver){
        //두 member의 dialog가 이미 있는지 확인
        MessageDialog dialog =  messageDialogRepository.findByMembers(sender, receiver);
        // 두 member의 dialog가 없다면 생성
        if(dialog == null){
            dialog = new MessageDialog();
            dialog.setOwner(sender);
            dialog.setPartnerId(receiver);
            messageDialogRepository.save(dialog);
        }
        return dialog;
    }

}
