package br.com.fiap.cliente.infrastructure.exceptions

data class BaseDeDadosException(override val message: String): RuntimeException(message)