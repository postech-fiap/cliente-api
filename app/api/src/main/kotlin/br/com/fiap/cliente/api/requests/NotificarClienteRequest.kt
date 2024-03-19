package br.com.fiap.cliente.api.requests

import br.com.fiap.cliente.domain.models.Notificar
import java.io.Serializable

data class NotificarClienteRequest(
    val idPedido: String,
    val idCliente: String,
    val numeroPedido: String,
    val status: PedidoStatus
) : Serializable {
    fun toModel() =
        Notificar(idCliente = idCliente, idPedido = idPedido, numeroPedido = numeroPedido, status = status.name)
}

