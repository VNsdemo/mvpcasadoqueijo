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

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getQuantidade() { return quantidade; }
    public double getEstoqueMinimo() { return estoqueMinimo; }
    public String getUnidade() { return unidade; }
    public double getPrecoCusto() { return precoCusto; }
    public Integer getFornecedorId() { return fornecedorId; }
    public String getFornecedorNome() { return fornecedorNome; }
}