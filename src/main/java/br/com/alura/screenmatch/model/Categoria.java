package br.com.alura.screenmatch.model;

public enum Categoria {

    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private final String categoriaOMDB;

    Categoria(String categoriaOMDB) {
        this.categoriaOMDB = categoriaOMDB;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOMDB.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para o gênero retornado.");
    }}

