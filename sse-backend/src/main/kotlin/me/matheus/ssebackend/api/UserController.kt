package me.matheus.ssebackend.api

import me.matheus.ssebackend.api.response.UserResponse
import me.matheus.ssebackend.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getAllUsers(): Flux<UserResponse> = userService.getAllUsers().map {
        UserResponse(it.id, it.name)
    }
}
