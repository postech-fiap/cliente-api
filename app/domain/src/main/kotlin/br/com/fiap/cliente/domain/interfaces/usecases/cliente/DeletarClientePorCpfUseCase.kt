package br.com.fiap.cliente.domain.interfaces.usecases.cliente

interface DeletarClientePorCpfUseCase {
    fun executar(cpf: String)
}