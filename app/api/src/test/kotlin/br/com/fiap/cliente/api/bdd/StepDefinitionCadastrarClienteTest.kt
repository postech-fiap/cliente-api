package br.com.fiap.cliente.api.bdd

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.http.HttpStatus

private const val ENDPOINT = "http://localhost:8080/clientes/cpf"
private const val VALID_CPF = "287.081.130-68"

class StepDefinitionCadastrarClienteTest {

    lateinit var response: Response

    @When("eu solicitar o cadastro de um novo cliente")
    fun cadastrarUmCliente() {
            response = given()
                .contentType("application/json")
                .body(
                    """
                        {
                            "cpf": $VALID_CPF,
                            "nome": "João da Silva",
                            "email": "teste@teste.com"
                            }
                            """.trimIndent()
                )
                .`when`()
                .post(ENDPOINT)
        }

    @Then("deve retornar o cliente cadastrado com sucesso")
    fun validarCadastroCliente() {
            response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body("cpf", equalTo(VALID_CPF))
        }
    }
