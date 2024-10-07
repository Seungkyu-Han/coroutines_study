package fastcampus.r2dbc.controller

import fastcampus.r2dbc.common.Image
import fastcampus.r2dbc.common.User
import fastcampus.r2dbc.controller.dto.ProfileImageResponse
import fastcampus.r2dbc.controller.dto.SignupUserRequest
import fastcampus.r2dbc.controller.dto.UserResponse
import fastcampus.r2dbc.service.UserCoroutineService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import java.util.function.Function


@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserCoroutineService
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @GetMapping("/{userId}")
    suspend fun getUserById(
        @PathVariable userId: Long
    ): UserResponse {
        val context = ReactiveSecurityContextHolder
            .getContext()
            .awaitSingle()

        val name = context.authentication.name
        if (name != userId.toString()) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return userService.findById(userId)
            ?.let(this::map)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    suspend fun signupUser(
        @RequestBody request: SignupUserRequest
    ): UserResponse {
        return userService.createUser(
            request.name, request.age,
            request.password, request.profileImageId
        ).let(this::map)
    }

    private fun map(user: User): UserResponse {
        return UserResponse(
            user.id,
            user.age,
            user.followCount,
            user.profileImage.map { image: Image ->
                ProfileImageResponse(
                    image.id,
                    image.name,
                    image.url
                )
            },
            user.name
        )
    }
}
