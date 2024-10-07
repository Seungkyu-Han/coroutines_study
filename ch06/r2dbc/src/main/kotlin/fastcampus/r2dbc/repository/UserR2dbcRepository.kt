package fastcampus.r2dbc.repository

import fastcampus.r2dbc.common.repository.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserR2dbcRepository: CoroutineCrudRepository<UserEntity, Long> {
}