package br.com.fiap.cliente.domain.usecases.cliente

import br.com.fiap.cliente.domain.interfaces.usecases.cliente.BuscarClientePorIdUseCase
import br.com.fiap.cliente.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.cliente.domain.interfaces.ClienteRepository

class BuscarClientePorIdUseCaseImpl(private val clienteRepository: ClienteRepository) : BuscarClientePorIdUseCase {
    override fun executar(id: Long) = clienteRepository.buscarPorId(id)
        .orElseThrow { RecursoNaoEncontradoException("Cliente não encontrado") }
}
