package br.com.fiap.cliente.domain.usecases.cliente

import br.com.fiap.cliente.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.cliente.domain.exceptions.RecursoJaExisteException
import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.models.Cliente

class CadastrarClienteUseCaseImpl(private val clienteRepository: ClienteRepository) : CadastrarClienteUseCase {
    override fun executar(cliente: Cliente): Cliente {

        clienteRepository.buscarPorCpf(cliente.cpf!!.numero)
            .takeIf { it != null }?.let {
                throw RecursoJaExisteException(String.format("CPF %s já está cadastrado", cliente.cpf))
            }

        return clienteRepository.salvar(cliente.valid())
    }
}
