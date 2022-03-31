package jpaproject.knockknock.config;

import jpaproject.knockknock.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //STOMP는 SOCKJS 방식으로 구동되기 때문에 withSockJS 해주어야 함
   @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
       registry.addEndpoint("/stompTest").setAllowedOrigins("*").withSockJS();
   }

   @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
       registry.enableSimpleBroker("/topic");
       registry.setApplicationDestinationPrefixes("/");
   }
}
