package me.matheus.ssebackend.api

import me.matheus.ssebackend.api.request.CreateUserRequest
import me.matheus.ssebackend.api.response.UserResponse
import me.matheus.ssebackend.domain.User
import me.matheus.ssebackend.service.UserService
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.time.Duration
import java.util.UUID
import java.util.stream.Stream


@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): Flux<UserResponse> = userService.getAllUsers().map {
        UserResponse(it.id, it.name)
    }

    @GetMapping("/updated-stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getNewUsers(): Flux<UserResponse> =
        Flux.zip(
            Flux.interval(Duration.ofMillis(5000)),
            Flux.fromStream(Stream.generate { userService.getAllUsers() })
        ).flatMap { obj: Tuple2<Long?, Flux<User>> ->
            obj.t2.map {
                println("returning ${it.id}")
                UserResponse(it.id, it.name)
            }
        }

    @GetMapping("/updated-stream-sse", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getNewUsersStreamSSE(): Flux<ServerSentEvent<UserResponse>> =
        Flux.zip(
            Flux.interval(Duration.ofMillis(5000)),
            Flux.fromStream(Stream.generate { userService.getAllUsers() })
        ).flatMap { obj: Tuple2<Long?, Flux<User>> ->
            obj.t2.map {
                println("returning ${it.id}")
                buildUserCreatedSSE(it.id, it.name)
            }
        }

    @PutMapping
    fun putNewUser(@RequestBody createUserRequest: CreateUserRequest): Mono<User> =
        userService.createNewUser(createUserRequest)

    private fun buildUserCreatedSSE(id: Long, name: String) =
        ServerSentEvent.builder<UserResponse>()
            .id(UUID.randomUUID().toString())
            .event("USER_CREATED")
            .data(UserResponse(id, name))
            .retry(Duration.ofSeconds(3))
            .build()
}
