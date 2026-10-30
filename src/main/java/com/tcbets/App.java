package com.tcbets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.*;
import com.sun.glass.ui.Menu;

import javafx.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.application.*;

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
public class App extends Application{

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

    @Override 
    public void start(Stage stage) {

        Label testo = new Label("TCBets");

        VBox layout = new VBox();

        layout.getChildren().add(testo);

        Scene cena = new Scene(layout, 800, 600);

        stage.setTitle("Pomekon");
        stage.setScene(cena);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
        
    }
}

@JsonIgnoreProperties(ignoreUnknown = true) // diz pra API do Jackson que pode ignorar o que não estiver na classe
class Pokemon {//so por enquanto é inner

    @JsonProperty("name")// seta o atributo nome da classe como name do json
    String nome;

    @JsonProperty("id") // seta id como id
    int id;
}