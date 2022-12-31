package snowball.kidsQuiz.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import snowball.kidsQuiz.handler.SignalHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebRtcConfig implements WebSocketConfigurer {

    private final SignalHandler singleHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(singleHandler, "/signal")
                .setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxTextMessageBufferSize(8192);
        return container;
    }


}