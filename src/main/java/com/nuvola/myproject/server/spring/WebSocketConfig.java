package com.nuvola.myproject.server.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.nuvola.myproject.server.websocket.QuotestWebSocketHandler;

import static com.nuvola.myproject.shared.ResourcePaths.QUOTES;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(quotesWebSocketHandler(), QUOTES);
    }

    @Bean
    public WebSocketHandler quotesWebSocketHandler() {
        return new QuotestWebSocketHandler();
    }
}
