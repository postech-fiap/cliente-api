
  Feature: Cliente

    Scenario: Cadastrar cliente

      When eu solicitar o cadastro de um novo cliente
      Then deve retornar o cliente cadastrado com sucesso


    Scenario: Buscar cliente
      When eu solicitar a busca de um cliente por cpf
      Then deve retornar o cliente buscado com sucesso
