package br.com.fiap.cliente.domain.exceptions

data class RecursoNaoEncontradoException(override val message: String) : RuntimeException(message)