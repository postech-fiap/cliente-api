package br.com.fiap.cliente.api.adapters.interfaces

import br.com.fiap.cliente.api.requests.CadastrarClienteRequest
import br.com.fiap.cliente.api.responses.ClienteResponse

interface ClienteAdapter {
    fun cadastrarCliente(request: CadastrarClienteRequest): ClienteResponse

    fun buscarClientePorCpf(cpf: String): ClienteResponse

    fun deletarClientePorCpf(cpf: String)
}
