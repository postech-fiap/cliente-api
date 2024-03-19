package br.com.fiap.cliente.infrastructure.entities

import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.models.Endereco
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cliente")
data class ClienteEntity(

    @Id
    val id: String? = null,
    val cpf: String? = null,
    val email: String? = null,
    val nome: String? = null,
    val telefone: String? = null,
    val endereco: Endereco? = null,
) {
    companion object {
        fun fromModel(cliente: Cliente) = ClienteEntity(
            id = cliente.id,
            cpf = cliente.cpf?.removeMascara(),
            nome = cliente.nome,
            email = cliente.email?.endereco,
            telefone = cliente.telefone,
            endereco = cliente.endereco,
        )
    }

    fun toModel() = Cliente(id = id, Cpf(Cpf.adicionaMascara(cpf)), nome, Email(email), telefone, endereco)
}
