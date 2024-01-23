package br.com.fiap.cliente.domain.usecases.cliente

import br.com.fiap.cliente.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.cliente.domain.interfaces.ClienteRepository
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

private const val CPF = "731.393.335-52"
private const val EMAIL = "test@test.com"

@ExtendWith(MockKExtension::class)
class BuscarClientePorCpfUseCaseImplTest {

    @InjectMockKs
    lateinit var buscarClientePorCpfUseCaseImpl: BuscarClientePorCpfUseCaseImpl

    @MockK
    lateinit var clientePort: ClienteRepository

    @Test
    fun `deve retornar um cliente por cpf quando existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(cpf = cpf, nome = nome, email = email)

        every { clientePort.buscarPorCpf(cliente.cpf!!.numero) } returns cliente

        //when
        val result = buscarClientePorCpfUseCaseImpl.executar(cliente.cpf!!.numero)

        //then
        Assertions.assertEquals(cliente.cpf, result.cpf)

        verify(exactly = 1) { clientePort.buscarPorCpf(cliente.cpf!!.numero) }
    }

    @Test
    fun `deve propagar um erro não encontrado quando o cliente não existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(cpf = cpf, nome = nome, email = email)

        every { cliente.cpf?.let { clientePort.buscarPorCpf(it.numero) } } returns null

        //when-then
        val exception = Assertions.assertThrows(RecursoNaoEncontradoException::class.java) {
            cliente.cpf?.let { buscarClientePorCpfUseCaseImpl.executar(it.numero) }
        }

        Assertions.assertEquals("CPF ${cpf.numero} não encontrado", exception.message)

        verify(exactly = 1) { cliente.cpf?.let { clientePort.buscarPorCpf(it.numero) } }
    }

    @Test
    fun `deve propagar erro quando ocorrer falha ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"

        every { cliente.cpf?.let { clientePort.buscarPorCpf(it.numero) } } throws RuntimeException(errorMessage)

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            cliente.cpf?.let { buscarClientePorCpfUseCaseImpl.executar(it.numero) }
        }

        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { cliente.cpf?.let { clientePort.buscarPorCpf(it.numero) } }
    }

}
