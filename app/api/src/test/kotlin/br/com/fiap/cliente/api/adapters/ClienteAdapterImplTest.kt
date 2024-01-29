package br.com.fiap.cliente.api.adapters

import br.com.fiap.cliente.api.requests.CadastrarClienteRequest
import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.cliente.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

private const val VALID_CPF = "287.081.130-68"
private const val EMAIL_TEST = "test@test.com"
@ExtendWith(MockKExtension::class)
class ClienteAdapterImplTest {

    lateinit var target: ClienteAdapterImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @MockK
    lateinit var cadastrarClienteUseCase: CadastrarClienteUseCase

    @MockK
    lateinit var buscarClienteUseCase: BuscarClientePorCpfUseCase

    @BeforeEach
    fun init() {
        target = ClienteAdapterImpl(
            cadastrarClienteUseCase,
            buscarClienteUseCase)
    }
    @Test
    fun `deve cadastrar um cliente com sucesso`() {

        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST
        )

        val cliente = Cliente(
            "1",
            nome = "João",
            cpf = Cpf(VALID_CPF),
            email = Email(EMAIL_TEST)
        )


        every { clienteRepository.salvar(any()) } returns cliente
        every { cadastrarClienteUseCase.executar(any()) } returns cliente

        val result = target.cadastrarCliente(clienteRequest)

        assertEquals(cliente.id, result.id)
        assertEquals(cliente.nome, result.nome)
        assertEquals(cliente.cpf?.numero, result.cpf)
        assertEquals(cliente.email?.endereco, result.email)

    }

    @Test
    fun `deve buscar um cliente com sucesso`() {

        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST
        )

        val cliente = Cliente(
            "1",
            nome = "João",
            cpf = Cpf(VALID_CPF),
            email = Email(EMAIL_TEST)
        )

        every { clienteRepository.buscarPorCpf(any()) } returns Optional.of(cliente)
        every { buscarClienteUseCase.executar(any()) } returns cliente

        val result = target.buscarClientePorCpf(clienteRequest.cpf)

        assertNotNull(result)
        assertEquals(cliente.id, result.id)
        assertEquals(cliente.nome, result.nome)
        assertEquals(cliente.cpf?.numero, result.cpf)
        assertEquals(cliente.email?.endereco, result.email)    }
}