package fastcampus.r2dbc.service

import fastcampus.r2dbc.common.Article
import fastcampus.r2dbc.common.EmptyImage
import fastcampus.r2dbc.common.Image
import fastcampus.r2dbc.common.User
import fastcampus.r2dbc.common.repository.AuthEntity
import fastcampus.r2dbc.common.repository.UserEntity
import fastcampus.r2dbc.repository.UserR2dbcRepository
import fastcampus.r2dbc.service.response.ImageResponse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*
import java.util.function.Function

@Service
class UserCoroutineService(
    private val userR2dbcRepository: UserR2dbcRepository,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {
    private val webClient = WebClient.create("http://localhost:8081")


    suspend fun findById(userId: Long): User? {
        val userEntity = userR2dbcRepository.findById(userId) ?: return null

        val imageId: String = userEntity.profileImageId
        val response = webClient.get().uri("/api/images/{imageId}", imageId)
            .retrieve()
            .bodyToMono<ImageResponse>(ImageResponse::class.java)
            .awaitSingle()
        val image = response.let{
            Image(
                it.id, it.name, it.url
            )
        }

        var profileImage: Optional<Image> = Optional.empty()
        if(image !is EmptyImage){
            profileImage = Optional.of(image)
        }
        return map(userEntity, profileImage)
    }

    @Transactional
    suspend fun createUser(name: String, age: Int, password: String, profileImageId: String): User {
        val newUser = UserEntity(
            id = null,
            name = name,
            age = age,
            password = password,
            profileImageId = profileImageId
        )
        val userEntity = userR2dbcRepository.save(newUser)
        val token = UUID.randomUUID().toString().substring(0, 6)

        val auth = AuthEntity(userEntity.id, token)

        r2dbcEntityTemplate.insert(auth).awaitSingle()
        return map(userEntity, Optional.empty())
    }

    private fun map(userEntity: UserEntity, profileImage: Optional<Image>): User {
        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.age,
            profileImage,
            listOf(),
            0L
        )
    }
}
