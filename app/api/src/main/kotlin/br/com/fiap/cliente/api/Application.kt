package br.com.fiap.cliente.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackages = ["br.com.fiap.cliente.*"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
