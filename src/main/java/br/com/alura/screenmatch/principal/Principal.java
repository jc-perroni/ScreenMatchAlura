package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    public void exibeMenu() {
        var menu = """
                1 - Buscar séries
                2 - Buscar episódios
                3 - Listar Séries
                0 - Sair                                 
                """;
        var opcao = -1;
        while (opcao != 0) {
            System.out.println(menu);
            try {
            opcao = leitura.nextInt();
            leitura.nextLine();

                switch (opcao) {
                    case 1 -> buscarSerieWeb();
                    case 2 -> buscarEpisodioPorSerie();
                    case 3 -> listarSeries();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida");
                }
            } catch (InputMismatchException | IllegalStateException e) {
                System.out.println("Erro verificado na entrada do menu. Tente novamente.");
                leitura.nextLine();
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        dadosSeries.add(dados);
        System.out.println(dados.titulo() + " incluída.");
    }

    private void listarSeries(){
        dadosSeries.forEach(x -> {
                    System.out.println("Nome da série: " + x.titulo());
                    System.out.println("Nota: " + x.avaliacao());
                    System.out.println("Total de temporadas: " + x.totalTemporadas());
                    System.out.println("Genero: " + x.genero());
                    System.out.println("Atores: " + x.atores());
                    System.out.println("Pôster: " + x.poster());
                    System.out.println("Sinopse: " + x.sinopse());
                    System.out.println();
                });
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

}