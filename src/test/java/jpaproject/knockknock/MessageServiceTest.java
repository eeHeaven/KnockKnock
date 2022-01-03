package jpaproject.knockknock;

import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.UserMessage;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.repository.message.MessageDialogRepository;
import jpaproject.knockknock.repository.message.UserMessageRepository;
import jpaproject.knockknock.requestForm.MessageForm;
import jpaproject.knockknock.service.MessageService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MessageServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MessageDialogRepository messageDialogRepository;
    @Autowired
    UserMessageRepository userMessageRepository;
    @Autowired
    MessageService messageService;

    @Test
    public void 메세지전송_새로운Dialog(){
        //given
        MessageForm messageForm = new MessageForm();
        messageForm.setMessage("테스트멤버 1->테스트멤버2 메세지");
        Member sender = memberRepository.findByNickName("테스트멤버1");
        Member receiver = memberRepository.findByNickName("테스트멤버2");
        messageForm.setReceiver(receiver.getId());
        messageForm.setSender(sender.getId());

        //when
       UserMessage message =  messageService.sendMessage(messageForm);

        //then
        Assertions.assertThat(sender.getMessagesWithUser().size()).isEqualTo(1);
        Assertions.assertThat(receiver.getMessagesWithUser().size()).isEqualTo(1);
        Assertions.assertThat(sender.getMessagesWithUser().get(0).getMessageList().get(0)).isEqualTo(message);
        Assertions.assertThat(receiver.getMessagesWithUser().get(0).getMessageList().get(0)).isEqualTo(message);
    }

    @Test
    public void 메세지전송_기존Dialog(){
        //given
        MessageForm messageForm = new MessageForm();
        messageForm.setMessage("테스트멤버 1->테스트멤버2 메세지");
        Member sender = memberRepository.findByNickName("테스트멤버1");
        Member receiver = memberRepository.findByNickName("테스트멤버2");
        messageForm.setReceiver(receiver.getId());
        messageForm.setSender(sender.getId());
        UserMessage message =  messageService.sendMessage(messageForm);

        MessageForm messageForm2 = new MessageForm();
        messageForm2.setMessage("테스트멤버 2->테스트멤버1 메세지");
         receiver = memberRepository.findByNickName("테스트멤버1");
        sender = memberRepository.findByNickName("테스트멤버2");
        messageForm2.setReceiver(sender.getId());
        messageForm2.setSender(receiver.getId());
        //when
        UserMessage savedMessage = messageService.sendMessage(messageForm2);
        //then
        Assertions.assertThat(sender.getMessagesWithUser().size()).isEqualTo(1);
        Assertions.assertThat(receiver.getMessagesWithUser().size()).isEqualTo(1);
        Assertions.assertThat(sender.getMessagesWithUser().get(0).getMessageList().get(1)).isEqualTo(savedMessage);
        Assertions.assertThat(receiver.getMessagesWithUser().get(0).getMessageList().size()).isEqualTo(2);

    }

}
