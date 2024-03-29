package br.com.fiap.cliente.domain.models

import br.com.fiap.cliente.domain.interfaces.Model
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email

data class Cliente(
    val id: String? = null,
    val cpf: Cpf? = null,
    val nome: String? = null,
    val email: Email? = null,
    val telefone: String? = null,
    val endereco: Endereco? = null,
) : Model {
    override fun valid(): Cliente {
        require(nome.isNullOrEmpty().not()) { "Nome não pode ser vazio" }
        return this
    }
}