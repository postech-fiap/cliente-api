package br.com.fiap.cliente.api.config

import br.com.fiap.cliente.api.adapters.ClienteAdapterImpl
import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.cliente.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.cliente.domain.usecases.cliente.BuscarClientePorCpfUseCaseImpl
import br.com.fiap.cliente.domain.usecases.cliente.BuscarClientePorIdUseCaseImpl
import br.com.fiap.cliente.domain.usecases.cliente.CadastrarClienteUseCaseImpl
import br.com.fiap.cliente.infrastructure.repositories.ClienteRepositoryImpl
import br.com.fiap.cliente.infrastructure.repositories.mongo.ClienteMongoRepository
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EntityScan(basePackages = ["br.com.fiap.cliente.*"])
class AppBeansConfig {
    @Bean
    fun clienteRepository(clienteMongoRepository: ClienteMongoRepository) = ClienteRepositoryImpl(clienteMongoRepository)

    @Bean
    fun cadastrarClienteUseCase(repository: ClienteRepository) = CadastrarClienteUseCaseImpl(repository)

    @Bean
    fun buscarClientePorCpfUseCase(repository: ClienteRepository) = BuscarClientePorCpfUseCaseImpl(repository)

    @Bean
    fun buscarClientePorIdUseCase(repository: ClienteRepository) = BuscarClientePorIdUseCaseImpl(repository)

    @Bean
    fun clienteFacade(
        clienteRepository: ClienteRepository,
        cadastrarClienteUseCase: CadastrarClienteUseCase,
        buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
    ) = ClienteAdapterImpl(
        cadastrarClienteUseCase,
        buscarClientePorCpfUseCase
    )

}
