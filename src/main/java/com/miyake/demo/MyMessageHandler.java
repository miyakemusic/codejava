package com.miyake.demo;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.entities.UserEntity;
import com.miyake.demo.repository.MyTesterRepository;
import com.miyake.demo.repository.UserRepository;

@Configuration
public class MyMessageHandler extends TextWebSocketHandler  {
	private Set<WebSocketSession> sessionsForBrowser = new HashSet<>();
	private Set<WebSocketSession> sessionsForTester = new HashSet<>();
	
	@Autowired
	private UserRepository userRepo;
	 
	@Autowired
	private MyTesterRepository testerRepo;
	
	@Bean
    public Set<WebSocketSession> getBrowserSessions() {
		return sessionsForBrowser;
	}

	@Bean
    public Set<WebSocketSession> getTesterSessions() {
		return sessionsForTester;
	}
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		Principal principal = session.getPrincipal();
		
		String name = principal.getName();
		
        UserEntity user = userRepo.findByEmail(name);
        if (user != null) {
        	sessionsForBrowser.add(session);
        }
        else {
        	MyTesterEntity myTester = testerRepo.findByName(name);
        	if (myTester != null) {
        		sessionsForTester.add(session);
        	}
        	else {
        		throw new UsernameNotFoundException("User not found");
        	}
        	
        }
		
    	
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
    	this.sessionsForBrowser.remove(session);
    	this.sessionsForTester.remove(session);
    }

}
