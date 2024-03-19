package br.com.fiap.cliente.api.requests

import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.models.Endereco
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email

data class CadastrarClienteRequest(
    val cpf: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val endereco: Endereco
) {
    init {
        require(nome.isNotBlank()) { "Nome não pode ser vazio" }
        require(cpf.isNotBlank()) { "Cpf não pode ser vazio" }
    }

    fun toModel() = Cliente(cpf = Cpf(cpf), nome = nome, email = Email(email), telefone = telefone, endereco = endereco)
}
