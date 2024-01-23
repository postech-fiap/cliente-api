package br.com.fiap.cliente.infrastructure.repositories.mongo

import br.com.fiap.cliente.infrastructure.entities.ClienteEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ClienteMongoRepository : MongoRepository<ClienteEntity, Long> {
    @Query("{ 'cpf': ?0 }")
    fun findByCpf(cpf: String): ClienteEntity?
}
