package br.com.fiap.cliente.api.bdd

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.restassured.RestAssured.given
import io.restassured.response.Response
import org.hamcrest.CoreMatchers.equalTo
import org.springframework.http.HttpStatus

private const val ENDPOINT = "http://localhost:8080/clientes/cpf/"
private const val VALID_CPF = "287.081.130-68"
class StepDefinitionBuscarCliente : CucumberTest() {

lateinit var response: Response


    @When("eu solicitar a busca de um cliente por cpf")
    fun buscarClientePorCpf() {
        response = given()
            .contentType("application/json")
            .`when`()
            .get("$ENDPOINT/$VALID_CPF")

    }

    @Then("deve retornar o cliente buscado com sucesso")
    fun validarBuscaCliente() {
        response.then()
            .statusCode(HttpStatus.OK.value())
            .body("cpf", equalTo(VALID_CPF))
    }

}