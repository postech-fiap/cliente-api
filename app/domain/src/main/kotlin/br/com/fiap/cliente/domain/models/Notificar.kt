package br.com.fiap.cliente.domain.models

data class Notificar(
    val idCliente: String,
    val idPedido: String,
    val numeroPedido: String,
    val status: String,
)