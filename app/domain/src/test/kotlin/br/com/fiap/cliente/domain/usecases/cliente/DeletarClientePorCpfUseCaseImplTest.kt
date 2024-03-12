package br.com.fiap.cliente.domain.usecases.cliente

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
private const val ID = "65b03e3d3b77462eae703b2b"

@ExtendWith(MockKExtension::class)
class DeletarClientePorCpfUseCaseImplTest {

    @InjectMockKs
    lateinit var deletarClientePorCpfUseCase: DeletarClientePorCpfUseCaseImpl

    @MockK
    lateinit var clienteRepository: ClienteRepository


    @Test
    fun `deve deletar um cliente com sucesso quando ele existir`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(id = ID, cpf = cpf, nome = nome, email = email)

        every { clienteRepository.salvar(cliente) } returns cliente
        every { clienteRepository.buscarPorCpf(cpf.numero) } returns Optional.of(cliente)
        every { clienteRepository.deletePorId(cliente.id!!) } returns Unit

        //when
        deletarClientePorCpfUseCase.executar(cpf.numero)

        //then
        verify(exactly = 1) { clienteRepository.deletePorId(cliente.id!!) }
        verify(exactly = 1) { clienteRepository.buscarPorCpf(cpf.numero) }
    }

    @Test
    fun `deve retornar erro quando ocorrer problema ao deletar cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(id = ID, cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"


        every { clienteRepository.salvar(cliente) } returns cliente
        every { clienteRepository.buscarPorCpf(cpf.numero) } returns Optional.of(cliente)
        every { clienteRepository.deletePorId(cliente.id!!) } throws RuntimeException(errorMessage)

        //when
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            deletarClientePorCpfUseCase.executar(cpf.numero)
        }

        //then
        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteRepository.deletePorId(cliente.id!!) }
        verify(exactly = 1) { clienteRepository.buscarPorCpf(cpf.numero) }
    }

    @Test
    fun `deve retornar erro quando ocorrer problema ao buscar o cliente`() {
        //given
        val cpf = Cpf(CPF)
        val nome = Random.nextLong().toString()
        val email = Email(EMAIL)
        val cliente = Cliente(id = ID, cpf = cpf, nome = nome, email = email)
        val errorMessage = "Erro na base de dados"


        every { clienteRepository.salvar(cliente) } returns cliente
        every { clienteRepository.buscarPorCpf(cpf.numero) } throws RuntimeException(errorMessage)

        //when
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            deletarClientePorCpfUseCase.executar(cpf.numero)
        }

        //then
        Assertions.assertEquals(errorMessage, exception.message)

        verify(exactly = 0) { clienteRepository.deletePorId(cliente.id!!) }
        verify(exactly = 1) { clienteRepository.buscarPorCpf(cpf.numero) }
    }
}