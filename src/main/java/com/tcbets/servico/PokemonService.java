package com.tcbets.servico;

import com.fasterxml.jackson.databind.*;
import java.net.http.*;
import java.net.URI;

public class PokemonService {

    private static final String URL =
        "https://pokeapi.co/api/v2/pokemon?limit=100000";

    public static String[] carregarPokemons() throws Exception {

        HttpClient cliente = HttpClient.newHttpClient();
        ObjectMapper conversor = new ObjectMapper();

        HttpRequest requisicao = HttpRequest.newBuilder()
            .uri(URI.create(URL))
            .GET()
            .build();

        HttpResponse<String> resposta = cliente.send(
            requisicao,
            HttpResponse.BodyHandlers.ofString()
        );

        JsonNode json = conversor.readTree(resposta.body());
        JsonNode resultados = json.get("results");

        String[] pokemons = new String[resultados.size()];

        for (int i = 0; i < pokemons.length; i++) {
            pokemons[i] = resultados.get(i).get("name").asText();
        }

        return pokemons;
    }

    public static String[] pesquisarPokemon(
        String pesquisa, String[] pokemons) {

        int quantidade = 0;

        for (String pokemon : pokemons) {
            if (pokemon.contains(pesquisa.toLowerCase())) {
                quantidade++;
            }
        }

        String[] encontrados = new String[quantidade];

        int indice = 0;

        for (String pokemon : pokemons) {
            if (pokemon.contains(pesquisa.toLowerCase())) {
                encontrados[indice] = pokemon;
                indice++;
            }
        }

        return encontrados;
    }
}