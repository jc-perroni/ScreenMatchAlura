package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaChatGpt;
import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.OptionalDouble;
@EntityScan
public class Serie {

    private String title;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;

    @Autowired
    public ConsultaChatGpt consulta;

    public Serie(DadosSerie dadosSerie) {
        this.title = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        try {
            System.out.println("Traduzindo a sinopse: ");
            System.out.println(dadosSerie.sinopse());
            this.sinopse = consulta.obterTraducao(dadosSerie.sinopse()).trim();
        }
        catch (Exception e){
            System.out.println("Não foi possível traduzir a sinopse usando chatgpt.");
            System.out.println("Problem -> " + e.getCause());
            System.out.println("A aplicação usará a API ConsultaMyMemory...");
            this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                "genero= " + genero +
                "title= " + title + '\'' +
                ", totalTemporadas= " + totalTemporadas +
                ", avaliacao= " + avaliacao +
                ", atores= " + atores + '\'' +
                ", poster= " + poster + '\'' +
                ", sinopse= " + sinopse + '\'' +
                '}';
    }
}
