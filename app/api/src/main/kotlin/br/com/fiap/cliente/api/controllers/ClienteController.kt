package br.com.fiap.cliente.api.controllers

import br.com.fiap.cliente.api.adapters.interfaces.ClienteAdapter
import br.com.fiap.cliente.api.requests.CadastrarClienteRequest
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val CPF_URI = "/cpf"

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteAdapter: ClienteAdapter) {

    @PostMapping(CPF_URI)
    fun cadastrarCliente(@RequestBody request: CadastrarClienteRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(clienteAdapter.cadastrarCliente(request))

    @GetMapping("$CPF_URI/{cpf}")
    fun buscarClientePorCpf(
        @PathVariable("cpf")
        @Parameter(name = "cpf", description = "CPF do cliente", example = "43253353425")
        cpf: String
    ) = ResponseEntity.status(HttpStatus.OK).body(clienteAdapter.buscarClientePorCpf(cpf))

    @DeleteMapping("$CPF_URI/{cpf}")
    fun deletarClientePorCpf(
        @PathVariable("cpf")
        @Parameter(name = "cpf", description = "CPF do cliente", example = "43253353425")
        cpf: String
    ) = ResponseEntity.status(HttpStatus.OK).body(clienteAdapter.deletarClientePorCpf(cpf))
}
