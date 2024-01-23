package br.com.fiap.cliente.domain.exceptions

data class RecursoJaExisteException(override val message: String) : RuntimeException(message)
