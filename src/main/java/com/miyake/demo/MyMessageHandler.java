package com.miyake.demo;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
public class MyMessageHandler extends TextWebSocketHandler  {
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	@Bean
    public Set<WebSocketSession> getSessions() {
		return sessions;
	}

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	sessions.add(session);
    	
//    	for (WebSocketSession s : sessions) {
//    		s.sendMessage(new TextMessage("Hello:" + session.toString()));
//    	}
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            TextMessage outputMessage = new TextMessage("Message:" + message.getPayload());
            session.sendMessage(outputMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	sessions.remove(session);
    }

}
