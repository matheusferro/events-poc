package me.matheus.ssebackend.service

import me.matheus.ssebackend.api.request.CreateUserRequest
import me.matheus.ssebackend.domain.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Service
class UserService {

    private val logger = Logger.getLogger(this.javaClass.simpleName)
    private val users = mutableListOf<User>().apply {
        for (i in 0..5) {
            this.add(User(i.toLong(), "test$i", "test$i@gmail.com"))
        }
    }

    fun getAllUsers(): Flux<User> {
        logger.info("returning users")
        return Flux.fromArray(users.toTypedArray())
    }

    fun createNewUser(createUserRequest: CreateUserRequest): Mono<User> {
        val user = User(users[users.lastIndex].id + 1, createUserRequest)
        logger.info("creating user ${user.id}")
        users.add(user)
        return Mono.just(user)
    }
}