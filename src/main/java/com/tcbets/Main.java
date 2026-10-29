package com.tcbets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.*;
import java.net.http.*;
import java.net.URI;

/*
vai ser um monte de pasta:
Modelo: pra classes
banco: pro sql
dao: pro CRUD
servico: tipo uma classe auxiliar
rede: pro servidor
e a classe Main vai ficar no source, fora de todas
*/

/*TESTE DO JACKSON COM POKEAPI!!!!!! */
public class Main {

    public static void testeHTTP1() throws Exception{

        HttpClient cliente = HttpClient.newHttpClient(); // pelo oq eu entendi, é como se o construtor dessa classe fosse privado

        HttpRequest requisição = HttpRequest.newBuilder() // cria uma requisição com o mesmo bagulho de construtor privado
            .uri(URI.create("https://pokeapi.co/api/v2/pokemon/Volcarona")) // seta a URL(URI) 
            .GET()// fala que eu quero consultar dados
            .build(); //finaliza e cria o objeto

        HttpResponse<String> resposta = cliente.send(requisição, // envia a requisição e espera a resposta
            HttpResponse.BodyHandlers.ofString()); // trata a resposta com string

        System.out.println("Código HTTP:" + resposta.statusCode()); // fala se deu merda ou não, se for 200 da bom
        System.out.println(resposta.body()); // imprime um puta json

    }

    public static void testeHTTP2() throws Exception{

        ObjectMapper conversor = new ObjectMapper(); // objeto que converte json pra classe
        HttpClient cliente = HttpClient.newHttpClient(); // pelo oq eu entendi, é como se o construtor dessa classe fosse privado

        HttpRequest requisição = HttpRequest.newBuilder() // cria uma requisição com o mesmo bagulho de construtor privado
            .uri(URI.create("https://pokeapi.co/api/v2/pokemon/Volcarona")) // seta a URL(URI) 
            .GET()// fala que eu quero consultar dados
            .build(); //finaliza e cria o objeto

        HttpResponse<String> resposta = cliente.send(requisição, // envia a requisição e espera a resposta
            HttpResponse.BodyHandlers.ofString()); // trata a resposta com string

        Pokemon pokemon = conversor.readValue(resposta.body(), Pokemon.class);// converte o json pra pokemon

        System.out.println(pokemon.id);
        System.out.println(pokemon.nome);

    }

    public static void main(String[] args) throws Exception /*fodase exceção*/{

        testeHTTP2();
        
    }
}

@JsonIgnoreProperties(ignoreUnknown = true) // diz pra API do Jackson que pode ignorar o que não estiver na classe
class Pokemon {//so por enquanto é inner

    @JsonProperty("name")// seta o atributo nome da classe como name do json
    String nome;

    @JsonProperty("id") // seta id como id
    int id;
}