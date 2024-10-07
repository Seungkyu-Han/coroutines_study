package fastcampus.mongodb.repository

import fastcampus.mongodb.entity.ChatDocument
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import reactor.core.publisher.Flux

interface ChatMongoCoroutineRepository:
    CoroutineCrudRepository<ChatDocument, ObjectId> {

    fun findAllByTo(to: String?): Flux<ChatDocument>
}