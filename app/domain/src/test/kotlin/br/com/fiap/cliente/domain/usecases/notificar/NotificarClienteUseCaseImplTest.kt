package br.com.fiap.cliente.domain.usecases.notificar

import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.models.Notificar
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.random.Random


private const val CPF = "731.393.335-52"
private const val EMAIL = "test@test.com"
private val outputStreamCaptor = ByteArrayOutputStream()


@ExtendWith(MockKExtension::class)
class NotificarClienteUseCaseImplTest {

    @InjectMockKs
    lateinit var target: NotificarClienteUseCaseImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @Test
    fun `dado uma nova atualizacao de pedido deve notificar o cliente com sucesso`() {
        //given
        val notificar = Notificar("1234556", "10", "05", "APROVADO")
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(cpf = cpf, nome = nome, email = email)
        val mensagem = "Olá ${cliente.nome}, o status do seu pedido foi alterado para ${notificar.status}."

        every { clienteRepository.buscarPorId(notificar.idCliente) } returns Optional.of(cliente)

        //then
        target.executar(notificar)

        //when
        assertEquals("Enviando email para ${email.endereco} com a mensagem: $mensagem", outputStreamCaptor.toString().trim())

        verify(exactly = 1) { clienteRepository.buscarPorId(notificar.idCliente) }

    }
}