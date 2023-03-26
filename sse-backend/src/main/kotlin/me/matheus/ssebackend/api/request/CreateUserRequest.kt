package me.matheus.ssebackend.api.request

data class CreateUserRequest (
    val name: String,
    val email: String
)