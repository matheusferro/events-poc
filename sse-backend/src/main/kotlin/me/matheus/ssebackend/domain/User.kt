package me.matheus.ssebackend.domain

import me.matheus.ssebackend.api.request.CreateUserRequest

data class User(
    val id: Long,
    val name: String,
    val email: String
) {
    constructor(id: Long, createUserRequest: CreateUserRequest): this(
        id = id,
        name = createUserRequest.name,
        email = createUserRequest.email
    )
}