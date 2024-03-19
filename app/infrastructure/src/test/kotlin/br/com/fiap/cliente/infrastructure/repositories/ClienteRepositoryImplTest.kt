package br.com.fiap.cliente.infrastructure.repositories

import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import br.com.fiap.cliente.infrastructure.entities.ClienteEntity
import br.com.fiap.cliente.infrastructure.repositories.mongo.ClienteMongoRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

private const val VALID_CPF = "287.081.130-68"
private const val EMAIL = "test@test.com"

@ExtendWith(MockKExtension::class)
class ClienteRepositoryImplTest {

    @MockK
    lateinit var clienteJpaRepository: ClienteMongoRepository

    @InjectMockKs
    lateinit var clienteRepository: ClienteRepositoryImpl

    @Test
    fun `deve salvar um cliente com sucesso`() {
        //given
        val cpf = VALID_CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val cliente = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)

        every { clienteJpaRepository.save(any()) } returns ClienteEntity.fromModel(cliente)

        //when
        val result = clienteRepository.salvar(cliente)

        //then
        assertEquals(cliente, result)
        assertEquals(cliente.id, result.id)
        assertEquals(cliente.nome, result.nome)
        assertEquals(cliente.cpf, result.cpf)
        assertEquals(cliente.email, result.email)

        verify(exactly = 1) { clienteJpaRepository.save(any()) }
    }

    @Test
    fun `deve buscar um cliente com sucesso`() {
        //given
        val cpf = VALID_CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val cliente = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val cpfSemMascara = Cpf.removeMascara(cpf)

        every { clienteJpaRepository.findByCpf(any()) } returns Optional.of(ClienteEntity.fromModel(cliente))

        //when
        val result = clienteRepository.buscarPorCpf(cpf)

        //then
        assertEquals(cliente, result.get())
        assertEquals(cliente.id, result.get().id)
        assertEquals(cliente.nome, result.get().nome)
        assertEquals(cliente.cpf, result.get().cpf)
        assertEquals(cliente.email, result.get().email)

        verify(exactly = 1) { clienteJpaRepository.findByCpf(cpfSemMascara) }
    }

    @Test
    fun `deve buscar um cliente com base no id com sucesso`() {
        //given
        val cpf = VALID_CPF
        val email = EMAIL
        val clienteId = "1"
        val nome = Random.nextLong().toString()
        val cliente = Cliente(id = clienteId, cpf = Cpf(cpf), email = Email(email), nome = nome)

        every { clienteJpaRepository.findById(clienteId) } returns Optional.of(ClienteEntity.fromModel(cliente))

        //when
        val result = clienteRepository.buscarPorId(clienteId)

        //then
        assertEquals(cliente, result.get())
        assertEquals(cliente.id, result.get().id)
        assertEquals(cliente.nome, result.get().nome)
        assertEquals(cliente.cpf, result.get().cpf)
        assertEquals(cliente.email, result.get().email)


        verify(exactly = 1) { clienteJpaRepository.findById(clienteId) }
    }

    @Test
    fun `deve propagar BaseDeDadosException quando ocorrer uma falha ao buscar um cliente por id`() {
        //given
        val errorMessage = "Erro ao buscar o cliente na base de dados. Detalhes: Error"
        val clienteId = "1"


        every { clienteJpaRepository.findById(clienteId) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            clienteRepository.buscarPorId(clienteId)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.findById(clienteId) }
    }


    @Test
    fun `deve propagar BaseDeDadosException quando ocorrer uma falha ao buscar um cliente`() {
        //given
        val cpf = VALID_CPF
        val cpfSemMascara = Cpf.removeMascara(cpf)
        val errorMessage = "Erro ao buscar o cliente na base de dados. Detalhes: Error"

        every { clienteJpaRepository.findByCpf(any()) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            clienteRepository.buscarPorCpf(cpf)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.findByCpf(cpfSemMascara) }
    }

    @Test
    fun `deve propagar BaseDeDadosException quando ocorrer uma falha ao salvar um cliente`() {
        //given
        val cpf = VALID_CPF
        val email = EMAIL
        val nome = Random.nextLong().toString()
        val errorMessage = "Erro ao salvar o cliente na base de dados. Detalhes: Error"
        val cliente = Cliente(cpf = Cpf(cpf), email = Email(email), nome = nome)
        val clienteEntity = ClienteEntity.fromModel(cliente)

        every { clienteJpaRepository.save(any()) } throws Exception("Error")

        //when-then
        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            clienteRepository.salvar(cliente)
        }

        //then
        assertEquals(errorMessage, exception.message)

        verify(exactly = 1) { clienteJpaRepository.save(clienteEntity) }
    }

    @Test
    fun `deve deletar cliente por id com sucesso`() {
        val clienteId = "123456"

        every { clienteJpaRepository.deleteById(clienteId) } returns Unit

        clienteRepository.deletePorId(clienteId)

        verify(exactly = 1) { clienteJpaRepository.deleteById(clienteId) }
    }

    @Test
    fun `deve retornar erro ao tentar deleter usuario com sucesso`() {
        val clienteId = "123456"

        every { clienteJpaRepository.deleteById(clienteId) } throws Exception("Error")

        val exception = Assertions.assertThrows(RuntimeException::class.java) {
            clienteRepository.deletePorId(clienteId)
        }

        assertEquals("Erro ao deletar o cliente na base de dados. Detalhes: Error",exception.message)
        verify(exactly = 1) { clienteJpaRepository.deleteById(clienteId) }
    }

}
