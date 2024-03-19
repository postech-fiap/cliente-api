package br.com.fiap.cliente.domain.usecases.cliente

import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.interfaces.usecases.cliente.DeletarClientePorCpfUseCase

class DeletarClientePorCpfUseCaseImpl(private val clienteRepository: ClienteRepository) : DeletarClientePorCpfUseCase {
    override fun executar(cpf: String) {
        clienteRepository.buscarPorCpf(cpf).let {
            clienteRepository.deletePorId(it.get().id!!)
        }
    }
}