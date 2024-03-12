package br.com.fiap.cliente.api.controllers

import IntegrationTest
import br.com.fiap.cliente.api.adapters.interfaces.ClienteAdapter
import br.com.fiap.cliente.api.requests.CadastrarClienteRequest
import br.com.fiap.cliente.api.responses.ClienteResponse
import br.com.fiap.cliente.domain.models.Cliente
import br.com.fiap.cliente.domain.models.Endereco
import br.com.fiap.cliente.domain.models.Estado
import br.com.fiap.cliente.domain.valueobjects.Cpf
import br.com.fiap.cliente.domain.valueobjects.Email
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

private const val VALID_CPF = "287.081.130-68"
private const val INVALID_CPF = "111.111.111-11"



private const val EMAIL_TEST = "test@test.com"

class ClienteControllerTest : IntegrationTest() {

    @MockkBean
    lateinit var clienteAdapter: ClienteAdapter

    @Test
    fun `deve retornar erro ao tentar salvar um cliente com cpf invalido`() {

        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = INVALID_CPF,
            email = EMAIL_TEST,
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        //when

        mockMvc.post("/clientes/cpf") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { is5xxServerError() }
            }
    }

    @Test
    fun `deve retornar erro ao tentar salvar um cliente com email em branco`() {

        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = "",
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        //when

        mockMvc.post("/clientes/cpf") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { is5xxServerError() }
            }
    }


    @Test
    fun `deve retornar erro ao tentar salvar um cliente com email em invalido`() {

        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = "x-1234@gmail.com",
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        //when

        mockMvc.post("/clientes/cpf") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { is5xxServerError() }
            }
    }


    @Test
    fun `deve salvar um cliente com sucesso`() {

        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST,
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        val cliente = ClienteResponse(
            Cliente(
                "1",
                nome = "João",
                cpf = Cpf(VALID_CPF),
                email = Email(EMAIL_TEST)
            )
        )

        every { clienteAdapter.cadastrarCliente(clienteRequest) } returns cliente

        //when

        mockMvc.post("/clientes/cpf") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { isCreated() }
                jsonPath("$.id") { value("1") }
                jsonPath("$.nome") { value("João") }
                jsonPath("$.cpf") { value(VALID_CPF) }
                jsonPath("$.email") { value(EMAIL_TEST) }
            }
    }

    @Test
    fun `deve buscar um cliente por CPF com sucesso`() {

        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST,
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        val cliente = ClienteResponse(
            Cliente(
                "1",
                nome = "João",
                cpf = Cpf(VALID_CPF),
                email = Email(EMAIL_TEST)
            ))

        every { clienteAdapter.buscarClientePorCpf(clienteRequest.cpf) } returns cliente

        //when

        mockMvc.get("/clientes/cpf/$VALID_CPF") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.id") { value("1") }
                jsonPath("$.nome") { value("João") }
                jsonPath("$.cpf") { value(VALID_CPF) }
                jsonPath("$.email") { value(EMAIL_TEST) }
            }
    }

    @Test
    fun `deve buscar um cliente por CPF que nao existe e retornar 404`() {
        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST,
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        //when-then
        mockMvc.get("/clientes/cpf/$VALID_CPF") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { is5xxServerError() }

            }
    }

    @Test
    fun `deve deletar um cliente por CPF com sucesso`() {
        //given
        val clienteRequest = CadastrarClienteRequest(
            nome = "João",
            cpf = VALID_CPF,
            email = EMAIL_TEST,
            telefone = "123456789",
            endereco = Endereco("Rua teste", "123", "Bairro teste", "Cidade teste", "Estado teste", Estado.ES)
        )

        every { clienteAdapter.deletarClientePorCpf(clienteRequest.cpf) } returns Unit

        //when-then
        mockMvc.delete("/clientes/cpf/$VALID_CPF") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(clienteRequest)
        }
            .andExpect {
                status { isOk() }
            }
    }
}