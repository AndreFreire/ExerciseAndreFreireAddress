# Exercicio de consulta de cep e crud de endereço.

## Descrição
Ambiente para a consulta de CEP e cadastro, alteração, leitura e exclusão de endereços em um banco de dados persistente, via API.

## Ambiente
java version "1.8.0_121" <br />  
Apache Maven 3.3.9 <br />  
MySql Ver 14.14 Distrib 5.5.54 <br />  

## Banco de dados
MySql 
Necessário criar um banco chamado `springbootdb` <br />
  `create database springbootdb;` <br /><br />
Configurado para usuário `root` e senha vazia. (Arquivo de configuração `src/main/resources/application.properties`)


## Consulta de cep
Consulta de cep feita no servico gratuito `https://viacep.com.br/`

## Endpoints

#### Inserção de endereço(POST):
   `http://localhost:8080/address/save/` <br />  
    body: `zipcode:<< CEP >>` <br />
⋅⋅⋅⋅⋅⋅⋅⋅`number: << number >>`<br />
⋅⋅⋅⋅⋅⋅⋅⋅`complement:<< complement >>`  (opcional)
          
#### Consulta de cep(GET):
  `http://localhost:8080/cep/<<CEP>>/`

#### Consulta de endereço(GET):
  `http://localhost:8080/address/get/<<idAddress>>/`
 
#### Remoção de endereço(DELETE):
  `http://localhost:8080/address/delete/<<idAddress>>/`

#### Alteração de endereço(POST):
   `http://localhost:8080/address/update/<<idAddress>>/` <br />
    body: `id: << ID >>` <br />
⋅⋅⋅⋅⋅⋅`zipcode:<< CEP >>` <br />
⋅⋅⋅⋅⋅⋅`number: << number >>` <br />
⋅⋅⋅⋅⋅⋅`complement:<< complement >>`  (opcional)
          
## Testes
`src/test/java/com/exercicioandrefreire/`

Att. <br />
Fico a disposição para possíveis dúvidas.
