package fastcampus.mongodb.handler

import lombok.AllArgsConstructor
import lombok.Data

data class Chat (
    var message: String?,
    var from: String?
)