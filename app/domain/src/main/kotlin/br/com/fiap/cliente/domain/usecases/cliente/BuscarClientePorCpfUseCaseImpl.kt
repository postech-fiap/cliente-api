package br.com.fiap.cliente.domain.usecases.cliente

import br.com.fiap.cliente.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.cliente.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.cliente.domain.interfaces.ClienteRepository

class BuscarClientePorCpfUseCaseImpl(private val clienteRepository: ClienteRepository) : BuscarClientePorCpfUseCase {
    override fun executar(cpf: String) =
        try {
            clienteRepository.buscarPorCpf(cpf)!!
        } catch (e: Exception) {
            throw RecursoNaoEncontradoException(String.format("CPF %s não encontrado", cpf))
        }
}
