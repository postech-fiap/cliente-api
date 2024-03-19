package br.com.fiap.cliente.api.controllers

import br.com.fiap.cliente.api.adapters.interfaces.NotificarClienteAdapter
import br.com.fiap.cliente.api.requests.NotificarClienteRequest
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class NotificarClienteController(private val notificarClienteAdparter: NotificarClienteAdapter) {

    @RabbitListener(queues = ["\${queue.pedido-alterado.name}"])
    private fun notificarCliente(request: NotificarClienteRequest) {
        println("Mensagem recebida: $request")
        ResponseEntity.status(HttpStatus.CREATED).body(notificarClienteAdparter.notificarCliente(request))
    }
}