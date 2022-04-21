package jpaproject.knockknock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jpaproject.knockknock.domain.Member;
import jpaproject.knockknock.domain.message.Message;
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

    @Before
    public void 초기데이터설정() {
        Member sender = new Member("보낸이", "sender", "1234");
        memberService.signUp(sender);
    }

    @Test
    public void 첫메세지전송() {
        //given
        Member sender = memberService.findByUserId("sender");
        Member receiver = memberService.findByUserId("receiver");
        //when
        messageService.sendFirstMessage(sender,receiver,"테스트입니다.");
        //then
        Assertions.assertThat(chatRoomService.findAllChatRooms().size()).isEqualTo(1);
        Assertions.assertThat(chatRoomService.findAllUserChatRooms().size()).isEqualTo(2);
    }




}
