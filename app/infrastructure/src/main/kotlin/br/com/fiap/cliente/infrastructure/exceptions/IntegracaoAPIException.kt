package br.com.fiap.cliente.infrastructure.exceptions

data class IntegracaoAPIException(override val message: String): RuntimeException(message)