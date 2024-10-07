package fastcampus.mongodb.handler

import fastcampus.mongodb.service.ChatCoroutineService
import fastcampus.mongodb.util.kLogger
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import reactor.core.publisher.Sinks.Many
import java.util.concurrent.ConcurrentHashMap


@Component
class ChatCoroutineWebSocketHandler(
    private val chatService: ChatCoroutineService
) : WebSocketHandler {

    companion object {
        private val chatSinkMap: MutableMap<String, Many<Chat>> = ConcurrentHashMap()
        private val log = kLogger()
    }

    override fun handle(session: WebSocketSession): Mono<Void> {
        return mono {


            val iam = session.attributes["iam"].toString()

            val chatFlux = chatService.register(iam)

            chatService.sendChat("system", iam, iam + "님 채팅방에 오신 것을 환영합니다.")

            session.receive().subscribe { webSocketMessage ->
                val payload = webSocketMessage.payloadAsText
                val splits =
                    payload.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val to = splits[0].trim { it <= ' ' }
                val message = splits[1].trim { it <= ' ' }

                launch {
                    chatService.sendChat(iam, to, message)
                }
            }
            session.send(chatFlux
                .map<WebSocketMessage> { chat: Chat -> session.textMessage(chat.from + ": " + chat.message) }
            )
                .doFinally { signalType: SignalType? ->
                    chatSinkMap.remove(iam)
                    log.info("WebSocket 연결이 종료되었습니다. {}의 sink를 제거합니다.", iam)
                }.awaitSingle()
        }
    }

}
