package br.com.fiap.cliente.infrastructure.repositories

import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.infrastructure.entities.ClienteEntity
import br.com.fiap.cliente.infrastructure.exceptions.BaseDeDadosException
import br.com.fiap.cliente.infrastructure.repositories.mongo.ClienteMongoRepository
import java.util.*

private const val ERROR_MESSAGE_TO_SAVE = "Erro ao salvar o cliente na base de dados. Detalhes: %s"
private const val ERROR_MESSAGE_TO_FIND = "Erro ao buscar o cliente na base de dados. Detalhes: %s"

class ClienteRepositoryImpl(private val clienteMongoRepository: ClienteMongoRepository) : ClienteRepository {

    override fun salvar(cliente: Cliente): Cliente {
        try {
            return clienteMongoRepository.save(ClienteEntity.fromModel(cliente))
                .toModel()
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_SAVE)
        }
    }

    override fun buscarPorCpf(cpf: String): Optional<Cliente> {
        try {
            return clienteMongoRepository.findByCpf(Cpf.removeMascara(cpf)).map { it.toModel() }
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
    }

    override fun buscarPorId(id: Long): Optional<Cliente> {
        try {
            return clienteMongoRepository.findById(id).map { it.toModel() }
        } catch (ex: Exception) {
            throw obterDataBaseException(ex, ERROR_MESSAGE_TO_FIND)
        }
    }

    private fun obterDataBaseException(ex: Exception, errorMessage: String) =
        BaseDeDadosException(String.format(errorMessage, ex.message))
}
