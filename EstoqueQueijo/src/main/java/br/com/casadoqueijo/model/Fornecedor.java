package br.com.casadoqueijo.model;

public record Fornecedor(int id, String nome, String telefone, String cidade) {
    public Fornecedor(String nome, String telefone, String cidade) { this(0, nome, telefone, cidade); }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getCidade() { return cidade; }
}