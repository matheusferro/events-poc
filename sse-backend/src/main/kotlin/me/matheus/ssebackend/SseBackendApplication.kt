package me.matheus.ssebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SseBackendApplication

fun main(args: Array<String>) {
	runApplication<SseBackendApplication>(*args)
}
