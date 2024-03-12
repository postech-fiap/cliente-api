package br.com.fiap.cliente.domain.interfaces.usecases.notificar

import br.com.fiap.cliente.domain.models.Notificar

interface NotificarClienteUseCase {
    fun executar(notificar: Notificar)
}