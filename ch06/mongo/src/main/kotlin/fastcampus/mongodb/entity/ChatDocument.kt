package fastcampus.mongodb.entity

import lombok.Data
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.PersistenceCreator
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Data
@Document(collection = "chat")
class ChatDocument @PersistenceCreator constructor(
    @field:Id var id: ObjectId?,
    var from: String?,
    var to: String?,
    var message: String?,
    @field:CreatedDate val createdAt: LocalDateTime?,
    @field:LastModifiedDate val updatedAt: LocalDateTime?
) {
    constructor(from: String?, to: String?, message: String?) : this(null, from, to, message, null, null)


    fun withId(id: ObjectId?): ChatDocument {
        return ChatDocument(id, this.to, this.from, this.message, this.createdAt, this.updatedAt)
    }
}
