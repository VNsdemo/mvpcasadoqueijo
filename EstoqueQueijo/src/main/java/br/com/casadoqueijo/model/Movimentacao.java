package br.com.casadoqueijo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Movimentacao(int id, int produtoId, String produtoNome, String tipo, double quantidade, LocalDateTime dataHora) {

    public int getId() { return id; }
    public int getProdutoId() { return produtoId; }
    public String getProdutoNome() { return produtoNome; }
    public String getTipo() { return tipo; }
    public double getQuantidade() { return quantidade; }
    public LocalDateTime getDataHora() { return dataHora; }

    // O <fmt:formatDate> não aceita LocalDateTime, então a data já sai formatada daqui
    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}