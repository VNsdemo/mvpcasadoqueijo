package br.com.casadoqueijo.model;

public record Produto(int id, String nome, String categoria, double quantidade, double estoqueMinimo,
                      String unidade, double precoCusto, Integer fornecedorId, String fornecedorNome) {
    public Produto(int id, String nome, String categoria, double quantidade, double estoqueMinimo,
                   String unidade, double precoCusto, Integer fornecedorId) {
        this(id, nome, categoria, quantidade, estoqueMinimo, unidade, precoCusto, fornecedorId, "");
    }
    public Produto(String nome, String categoria, double quantidade, double estoqueMinimo,
                   String unidade, double precoCusto, Integer fornecedorId) {
        this(0, nome, categoria, quantidade, estoqueMinimo, unidade, precoCusto, fornecedorId, "");
    }
}
