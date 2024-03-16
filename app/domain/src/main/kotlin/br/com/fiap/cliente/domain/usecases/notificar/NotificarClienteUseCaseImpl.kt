package br.com.fiap.cliente.domain.usecases.notificar

import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.interfaces.usecases.notificar.NotificarClienteUseCase
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.models.Notificar

class NotificarClienteUseCaseImpl(private val repository: ClienteRepository) : NotificarClienteUseCase {
    override fun executar(notificar: Notificar) {
        return repository.buscarPorId(notificar.idCliente)
            .let { cliente ->
                val message = buscarMensagem(cliente.get(), notificar)
                enviarEmail(cliente.get().email?.endereco, message)
            }
    }

    private fun buscarMensagem(cliente: Cliente, notificar: Notificar): String {
        val mensagem = "Olá ${cliente.nome}, o status do seu pedido foi alterado para ${notificar.status}."
        return mensagem
    }

    private fun enviarEmail(email: String?, message: String) {
        println("Enviando email para $email com a mensagem: $message")
    }

}