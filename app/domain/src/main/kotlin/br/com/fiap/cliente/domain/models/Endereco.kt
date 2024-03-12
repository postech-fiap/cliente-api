package br.com.fiap.cliente.domain.models

data class Endereco(
    val logradouro: String? = null,
    val numero: String? = null,
    val complemento: String? = null,
    val bairro: String? = null,
    val cidade: String? = null,
    val estado: Estado? = null,
    val cep: String? = null,
)