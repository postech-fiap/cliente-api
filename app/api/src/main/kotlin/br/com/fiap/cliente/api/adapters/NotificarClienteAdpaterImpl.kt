package br.com.fiap.cliente.api.adapters

import br.com.fiap.cliente.api.adapters.interfaces.NotificarClienteAdapter
import br.com.fiap.cliente.api.requests.NotificarClienteRequest
import br.com.fiap.cliente.domain.interfaces.usecases.notificar.NotificarClienteUseCase

class NotificarClienteAdpaterImpl(
    private val notificarClienteUseCase: NotificarClienteUseCase
) : NotificarClienteAdapter {
    override fun notificarCliente(notificarClienteRequest: NotificarClienteRequest) {
        return notificarClienteUseCase.executar(notificarClienteRequest.toModel())
    }
}