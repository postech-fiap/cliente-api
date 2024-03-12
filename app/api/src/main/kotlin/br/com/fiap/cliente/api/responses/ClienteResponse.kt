package br.com.fiap.cliente.api.responses

import br.com.fiap.cliente.domain.models.Cliente

class ClienteResponse(cliente: Cliente) {
    val id = cliente.id
    val cpf = cliente.cpf?.numero
    val nome = cliente.nome
    val email = cliente.email?.endereco
    val telefone = cliente.telefone
    val endereco = cliente.endereco
}
