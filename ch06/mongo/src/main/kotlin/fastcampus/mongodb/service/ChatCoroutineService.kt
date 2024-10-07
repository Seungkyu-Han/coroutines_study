package fastcampus.mongodb.service

import com.mongodb.client.model.changestream.OperationType
import fastcampus.mongodb.entity.ChatDocument
import fastcampus.mongodb.handler.Chat
import fastcampus.mongodb.repository.ChatMongoCoroutineRepository
import fastcampus.mongodb.util.kLogger
import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many
import java.util.concurrent.ConcurrentHashMap


@Service
@Slf4j
@RequiredArgsConstructor
class ChatCoroutineService(
    private val chatMongoRepository: ChatMongoCoroutineRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    companion object {
        private val chatSinkMap: MutableMap<String, Many<Chat>> = ConcurrentHashMap()
        private val log = kLogger()
    }

    @PostConstruct
    fun init() {
        reactiveMongoTemplate.changeStream(ChatDocument::class.java)
            .listen()
            .doOnNext { item: ChangeStreamEvent<ChatDocument> ->
                val target = item.body
                val operationType = item.operationType
                if (target != null && operationType == OperationType.INSERT) {
                    val from = target.from
                    val to = target.to
                    val message = target.message

                    doSend(from, to, message)
                }
            }.subscribe()
    }

    fun register(iam: String): Flux<Chat> {
        val sink = Sinks.many().unicast().onBackpressureBuffer<Chat>()

        chatSinkMap[iam] = sink

        chatMongoRepository.findAllByTo(iam)
            .doOnNext { chat: ChatDocument ->
                sink.tryEmitNext(Chat(chat.message, chat.to))
            }
            .subscribe()


        return sink.asFlux()
    }

    suspend fun sendChat(from: String?, to: String?, message: String?) {
        log.info("from: {}, to: {}, message: {}", from, to, message)
        val chat = ChatDocument(from, to, message)
        chatMongoRepository.save(chat)
    }

    fun doSend(from: String?, to: String?, message: String?) {
        val sink = chatSinkMap[to]
        if (sink == null) {
            val my = chatSinkMap[from]!!
            my.tryEmitNext(Chat("대화 상대가 없습니다.", "system"))
            return
        }
        sink.tryEmitNext(Chat(message, from))
    }

}
