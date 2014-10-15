package com.nuvola.myproject.server.websocket;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonWebSocketHandler<I, O> extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final Class<I> inputType;

    public JsonWebSocketHandler(Class<I> inputType) {
        this.inputType = inputType;
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        handleObject(session, mapper.readValue(message.getPayload(), inputType));
    }

    public void sendObject(WebSocketSession session, O object) throws Exception {
        session.sendMessage(new TextMessage(mapper.writeValueAsString(object)));
    }

    public abstract void handleObject(WebSocketSession session, I object) throws Exception;
}
