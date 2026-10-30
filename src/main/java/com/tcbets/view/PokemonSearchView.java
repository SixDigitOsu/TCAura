
package com.tcbets.view;

import com.tcbets.servico.PokemonService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PokemonSearchView extends Application {

    private TextField campoPesquisa;
    private ListView<String> listaPokemon;
    private Label status;

    private ObservableList<String> pokemons =
        FXCollections.observableArrayList();

    private FilteredList<String> filtrados =
        new FilteredList<>(pokemons);

    @Override
    public void start(Stage janela) {

        campoPesquisa = new TextField();
        campoPesquisa.setPromptText("Digite o nome do Pokemon...");

        listaPokemon = new ListView<>();
        listaPokemon.setItems(filtrados);

        status = new Label("Carregando Pokemon...");

        campoPesquisa.setDisable(true);

        // Atualiza os resultados conforme o usuário digita
        campoPesquisa.textProperty().addListener(
            (observable, anterior, atual) -> {

                String pesquisa = atual.toLowerCase().trim();

                filtrados.setPredicate(pokemon ->
                    !pesquisa.isEmpty() &&
                    pokemon.toLowerCase().startsWith(pesquisa)
                );

                status.setText(
                    filtrados.size() + " resultados encontrados"
                );
            }
        );

        // Carrega os Pokemon sem congelar a janela
        Task<String[]> tarefa = new Task<>() {
            @Override
            protected String[] call() throws Exception {
                return PokemonService.carregarPokemons();
            }
        };

        tarefa.setOnSucceeded(event -> {
            pokemons.setAll(tarefa.getValue());

            campoPesquisa.setDisable(false);
            status.setText("Digite para pesquisar");

            campoPesquisa.requestFocus();
        });

        tarefa.setOnFailed(event -> {
            status.setText("Erro ao carregar os Pokemon.");

            tarefa.getException().printStackTrace();
        });

        Thread thread = new Thread(tarefa);
        thread.setDaemon(true);
        thread.start();

        VBox layout = new VBox(10,
            new Label("Pesquisar Pokemon"),
            campoPesquisa,
            listaPokemon,
            status
        );

        layout.setStyle("-fx-padding: 20;");

        Scene cena = new Scene(layout, 350, 450);

        janela.setTitle("Pokemon Search");
        janela.setScene(cena);
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
