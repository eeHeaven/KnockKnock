package jpaproject.knockknock;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.Message.ChatRoomService;
import jpaproject.knockknock.service.Message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
@Slf4j
@AutoConfigureMockMvc
public class MessageTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    MessageService messageService;
    @Autowired
    MemberService memberService;

    @Before
    public void 초기데이터설정() {
        Member sender = new Member("보낸이", "sender", "1234");
        memberService.signUp(sender);
        Member receiver = new Member("받는이", "receiver", "1234");
        memberService.signUp(receiver);
    }

    @Test
    public void 첫메세지전송() {
        //given
        Member sender = memberService.findByUserId("sender");
        Member receiver = memberService.findByUserId("receiver");
        //when
        messageService.sendFirstMessage(sender.getUserId(), receiver.getUserId(), "테스트입니다.");
        //then
        Assertions.assertThat(sender.getChatRooms().get(0).getChatRoom().getMessages().get(0).getMessage()).isEqualTo("테스트입니다.");
        Assertions.assertThat(receiver.getChatRooms().get(0).getChatRoom().getMessages().get(0).getMessage()).isEqualTo("테스트입니다.");
    }

    @Test
    public void 두번째이후메세지전송(){
        //given
        Member sender = memberService.findByUserId("sender");
        Member receiver = memberService.findByUserId("receiver");
        messageService.sendFirstMessage(sender.getUserId(), receiver.getUserId(), "첫번째 메세지입니다.");
        //when
        messageService.sendFirstMessage(sender.getUserId(), receiver.getUserId(), "두번째 메세지입니다.");
        //then
        Assertions.assertThat(sender.getChatRooms().get(0).getChatRoom().getMessages().get(1).getMessage()).isEqualTo("첫번째 메세지입니다.");
        Assertions.assertThat(receiver.getChatRooms().get(0).getChatRoom().getMessages().get(1).getMessage()).isEqualTo("첫번째 메세지입니다.");
        Assertions.assertThat(sender.getChatRooms().get(0).getChatRoom().getMessages().get(0).getMessage()).isEqualTo("두번째 메세지입니다.");
        Assertions.assertThat(receiver.getChatRooms().get(0).getChatRoom().getMessages().get(0).getMessage()).isEqualTo("두번째 메세지입니다.");
    }



}
