package fastcampus.mongodb.config

import fastcampus.mongodb.handler.ChatCoroutineWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler

@Configuration
class MappingConfig {
    @Bean
    fun simpleUrlHandlerMapping(
        chatWebSocketHandler: ChatCoroutineWebSocketHandler?
    ): SimpleUrlHandlerMapping {
        val urlMapper = mapOf<String, WebSocketHandler?>(
            "/chat" to chatWebSocketHandler
        )

        val mapping = SimpleUrlHandlerMapping()

        mapping.order = 1
        mapping.urlMap = urlMapper

        return mapping
    }
}
