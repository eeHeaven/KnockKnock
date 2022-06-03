package jpaproject.knockknock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jpaproject.knockknock.api.request.MessageRequest;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.Message;
import jpaproject.knockknock.repository.MemberRepository;
import jpaproject.knockknock.service.MemberService;
import jpaproject.knockknock.service.Message.ChatRoomService;
import jpaproject.knockknock.service.Message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
@Slf4j
@AutoConfigureMockMvc
public class ChatRoomTest {

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
    @Autowired
    MemberRepository memberRepository;

    @Before
    public void 초기데이터설정() {
        Member sender = Member.memberBuilder().userId("sender").userPassword("1234").nickName("보낸이").build();
        memberRepository.save(sender);
        Member receiver = Member.memberBuilder().userId("receiver").userPassword("5678").nickName("받는이").build();
        memberRepository.save(receiver);
    }

    @Test
    public void 첫메세지전송시채팅방생성() {
        //given
        MessageRequest messageRequest = MessageRequest.builder()
                .senderId("sender")
                .receiverId("receiver")
                .message("테스트입니다")
                .build();
        //when
        messageService.sendMessage(messageRequest);
        //then
        Assertions.assertThat(chatRoomService.findAllChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(chatRoomService.findAllUserChatRooms().size()).isEqualTo(2);
        Member sender = memberService.findByUserId("sender");
        Member receiver = memberService.findByUserId("receiver");
        Assertions.assertThat(sender.getChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(receiver.getChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(sender.getChatRooms().get(0).getPartenerNickname()).isEqualTo("받는이");
        Assertions.assertThat(receiver.getChatRooms().get(0).getPartenerNickname()).isEqualTo("보낸이");
    }

    @Test
    public void 두번째메세지이후부터채팅방생성안됨(){
        //given
        MessageRequest messageRequest = MessageRequest.builder()
                .senderId("sender")
                .receiverId("receiver")
                .message("테스트입니다")
                .build();
        messageService.sendMessage(messageRequest);
        //when
        MessageRequest messageRequest2 = MessageRequest.builder()
                .senderId("sender")
                .receiverId("receiver")
                .message("두번째 메세지입니다.")
                .build();
        messageService.sendMessage(messageRequest2);
        //then
        Member sender = memberService.findByUserId("sender");
        Member receiver = memberService.findByUserId("receiver");
        Assertions.assertThat(chatRoomService.findAllChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(chatRoomService.findAllUserChatRooms().size()).isEqualTo(2);
        Assertions.assertThat(sender.getChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(receiver.getChatRooms().size()).isEqualTo(1);
    }



}
