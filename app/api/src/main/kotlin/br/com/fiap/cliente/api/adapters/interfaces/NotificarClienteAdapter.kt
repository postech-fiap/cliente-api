package br.com.fiap.cliente.api.adapters.interfaces

import br.com.fiap.cliente.api.requests.NotificarClienteRequest

interface NotificarClienteAdapter {

    fun notificarCliente(notificarClienteRequest: NotificarClienteRequest)
}