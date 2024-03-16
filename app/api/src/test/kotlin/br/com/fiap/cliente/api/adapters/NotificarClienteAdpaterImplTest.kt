package br.com.fiap.cliente.api.adapters

import br.com.fiap.cliente.api.requests.NotificarClienteRequest
import br.com.fiap.cliente.api.requests.PedidoStatus
import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.interfaces.usecases.notificar.NotificarClienteUseCase
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

private const val CPF = "731.393.335-52"
private const val EMAIL = "test@test.com"

@ExtendWith(MockKExtension::class)
class NotificarClienteAdpaterImplTest {

    lateinit var target: NotificarClienteAdpaterImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @MockK
    lateinit var notificarClienteUseCase: NotificarClienteUseCase

    @BeforeEach
    fun init() {
        target = NotificarClienteAdpaterImpl(
            notificarClienteUseCase
        )
    }

    @Test
    fun `deve notificar o cliente com sucesso`() {

        //given
        val notificarClienteRequest = NotificarClienteRequest("12345", "1","10", PedidoStatus.APROVADO)
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(cpf = cpf, nome = nome, email = email)
        val notificar = notificarClienteRequest.toModel()

        every { clienteRepository.buscarPorId(notificar.idCliente) } returns Optional.of(cliente)
        every { notificarClienteUseCase.executar(notificarClienteRequest.toModel()) } returns Unit

        //then
        target.notificarCliente(notificarClienteRequest)

        //when

        verify(exactly = 1) { notificarClienteUseCase.executar(notificarClienteRequest.toModel()) }

    }
}