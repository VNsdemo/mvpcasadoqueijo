package br.com.casadoqueijo.model;

public record Fornecedor(int id, String nome, String telefone, String cidade) {
    public Fornecedor(String nome, String telefone, String cidade) { this(0, nome, telefone, cidade); }
}
