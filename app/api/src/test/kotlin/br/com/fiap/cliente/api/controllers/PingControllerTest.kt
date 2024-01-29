package br.com.fiap.cliente.api.controllers

import IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get

class PingControllerTest : IntegrationTest() {

    @Test
    fun `deve retornar 200 ok ao buscar o ping com sucesso`() {
        //when-then
        mockMvc.get("/ping") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
            }
    }
}
