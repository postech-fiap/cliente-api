package br.com.fiap.cliente.domain.exceptions

data class BusinessException(override val message: String): RuntimeException(message)