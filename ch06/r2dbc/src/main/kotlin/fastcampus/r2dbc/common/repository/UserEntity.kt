package fastcampus.r2dbc.common.repository

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("USER")
data class UserEntity (
    @Id
    var id: Long?,
    val name: String,
    val age: Int,
    val profileImageId: String,
    val password: String,

    @CreatedDate var createdAt: LocalDateTime? = null,
    @LastModifiedDate var updatedAt: LocalDateTime? = null
)