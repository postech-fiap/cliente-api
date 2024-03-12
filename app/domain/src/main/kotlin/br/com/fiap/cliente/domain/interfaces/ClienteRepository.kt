package br.com.fiap.cliente.domain.interfaces

import br.com.fiap.cliente.domain.models.Cliente
import java.util.*

interface ClienteRepository {

    fun salvar(cliente: Cliente): Cliente
    fun buscarPorCpf(cpf: String): Optional<Cliente>
    fun buscarPorId(id: String): Optional<Cliente>
    fun deletePorId(id: String)
}
