package br.com.fiap.cliente.domain.interfaces.usecases.cliente

import br.com.fiap.cliente.domain.models.Cliente

fun interface CadastrarClienteUseCase {

    fun executar(cliente: Cliente): Cliente
}